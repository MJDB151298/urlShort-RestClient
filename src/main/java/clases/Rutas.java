package clases;

//import Url;
//import Clases.Usuario;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.apache.commons.codec.cli.Digest;
import org.apache.commons.codec.digest.DigestUtils;
import spark.Session;
import spark.Spark;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.*;

import static spark.Spark.before;
import static spark.Spark.halt;

public class Rutas {
    Map<String, String> headers = new HashMap<>();

    public void manejoRutas(){
        headers.put("accept", "applcation/json");
        final Configuration configuration = new Configuration(new Version(2, 3, 0));
        try {
            configuration.setDirectoryForTemplateLoading(new File(
                    "src/main/java/resources/spark/template/freemarker"));
        } catch(IOException e) {
            e.printStackTrace();
        }

        Spark.get("/requestToken", (request, response) -> {
            HttpResponse<JsonNode> requestToken = Unirest.post("http://localhost:4567/token").asJson();
            String token = requestToken.getBody().getObject().getString("token");
            System.out.println(token);
            headers.put("token", token);
            return "";
        });

        Spark.get("/menu/:pageNumber", (request, response) -> {
            HttpResponse<String> sizeUrls = Unirest.get("http://localhost:4567/Urls/size")
                    .header("accept", "application/json")
                    .asString();
            int pageNumber = Integer.parseInt(request.params("pageNumber"));
            Map<String, Object> attributes = new HashMap<>();
            Usuario loggedUser = request.session(true).attribute("usuario");
            attributes.put("loggedUser", loggedUser);
            attributes.put("pageNumber", pageNumber);
            try{
                attributes.put("sizeAllLinks", Long.parseLong(sizeUrls.getBody()));
            }catch(NumberFormatException e){
                attributes.put("sizeAllLinks", 0);
            }

            Url[] urls;
            if(loggedUser == null){
                HttpResponse<String> requestAnonUrls = Unirest.get("http://localhost:4567/Urls/anonUrls")
                        .headers(headers)
                        .asString();
                Url[] anonUrls = new Gson().fromJson(requestAnonUrls.getBody(), (Type) Url[].class);
                if(anonUrls != null){
                    attributes.put("links", anonUrls);
                }
                else{
                    attributes.put("links", new ArrayList<>());
                }
            }
            else if(loggedUser.isAdministrador() == true){
                HttpResponse<String> requestUrls = Unirest.get("http://localhost:4567/Urls/pagination/{pageNumber}")
                        .headers(headers)
                        .routeParam("pageNumber", Integer.toString(pageNumber))
                        .asString();
                urls = new Gson().fromJson(requestUrls.getBody(), (Type) Url[].class);
                attributes.put("links", urls);
            }
            else if(loggedUser != null){
                HttpResponse<String> requestUrls = Unirest.get("http://localhost:4567/Urls/paginationID/{pageNumber}/{id}")
                        .headers(headers)
                        .routeParam("pageNumber", Integer.toString(pageNumber))
                        .routeParam("id", loggedUser.getId())
                        .asString();
                urls = new Gson().fromJson(requestUrls.getBody(), (Type) Url[].class);
                attributes.put("links", urls);
            }
            //return "";
            return getPlantilla(configuration, attributes, "index.ftl");
        });

        Spark.post("/createUrl", (request, response) -> {
            String originalUrl = request.queryParams("originalUrl");
            Usuario usuario = request.session().attribute("usuario");
            Unirest.post("http://localhost:4567/Urls/createUrl")
                    .headers(headers)
                    .body(usuario)
                    .queryString("originalUrl", originalUrl)
                    .asEmpty();
            response.redirect("/menu/1");
            return "";
        });

        Spark.get("shorty/:index", (request, response) -> {
            String urlShort = request.params("index");
            Usuario usuario = request.session().attribute("usuario");
            HttpResponse<String> requestUrl = Unirest.get("http://localhost:4567/Visitas/shorty/{index}/{usuarioID}")
                    .headers(headers)
                    .routeParam("index", urlShort)
                    .routeParam("usuarioID", usuario.getId())
                    .asString();
            Url url = new Gson().fromJson(requestUrl.getBody(), (Type) Url.class);
            //System.out.println(url.getUrlOriginal());
            if(url != null){
                response.redirect("http://" + url.getUrlOriginal());
            }
            else{
                System.out.println("Going nowhere!");
                Map<String, Object> attributes = new HashMap<>();
                attributes.put("loggedUser", usuario);
                return getPlantilla(configuration, attributes, "notFound.ftl");
            }
            return "";
        });

        Spark.get("stats/:index", (request, response) -> {
            String urlid = request.params("index");
            HttpResponse<String> getDaysOfWeek = Unirest.get("http://localhost:4567/Visitas/statsDaysOfWeek/{id}/")
                    .headers(headers)
                    .routeParam("id", urlid)
                    .asString();
            HttpResponse<String> getNavegadores = Unirest.get("http://localhost:4567/Visitas/statsNavegadores/{id}/")
                    .headers(headers)
                    .routeParam("id", urlid)
                    .asString();
            HttpResponse<String> getHoras = Unirest.get("http://localhost:4567/Visitas/statsHora/{id}/")
                    .headers(headers)
                    .routeParam("id", urlid)
                    .asString();
            Long[] days = new Gson().fromJson(getDaysOfWeek.getBody(), Long[].class);
            Long[] navegadores = new Gson().fromJson(getNavegadores.getBody(), Long[].class);
            Long[] horas = new Gson().fromJson(getHoras.getBody(), Long[].class);

            Map<String, Object> attributes = new HashMap<>();
            Usuario loggedUser = request.session().attribute("usuario");
            attributes.put("loggedUser", loggedUser);

            attributes.put("days", days);
            attributes.put("navegadores", navegadores);
            attributes.put("horas", horas);
            return getPlantilla(configuration, attributes, "stats.ftl");
        });

        Spark.get("/eliminarUrl/:index", (request, response) -> {
            String urlid = request.params("index");
            Unirest.delete("http://localhost:4567/Urls/delete/{index}")
                    .headers(headers)
                    .routeParam("index", urlid)
                    .asString();
            response.redirect("/menu/1");
            return "";
        });

        Spark.get("/administradores", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            HttpResponse<String> getUsuarios = Unirest.get("http://localhost:4567/usuarios/")
                    .headers(headers)
                    .asString();
            Usuario[] usuarios = new Gson().fromJson(getUsuarios.getBody(), (Type) Usuario[].class);
            attributes.put("listaUsuarios", usuarios);
            return getPlantilla(configuration, attributes, "administrador.ftl");
        });

        Spark.put("/makeAdministrador/:username", (request, response) -> {
            String username = request.params("username");
            System.out.println("Klk");
            Unirest.put("http://localhost:4567/usuarios/activarAdministrador/{username}/{checkAdministrador}")
                    .headers(headers)
                    .routeParam("username", username)
                    .routeParam("checkAdministrador", request.queryParams("checkAdministrador"));
            response.redirect("/administradores");
            return "";
        });


        Spark.get("/login", (request, response) -> {
            String warningText = "";
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("warningText", warningText);
            return getPlantilla(configuration, attributes, "login.ftl");
        });

        Spark.post("/login", (request, response) -> {
            String username = request.queryParams("username");
            String password = request.queryParams("password");
            String hashedPassword = DigestUtils.md5Hex(password);
            HttpResponse<String> validateUser = Unirest.get("http://localhost:4567/usuarios/validarLogeo/{username}/{passwordHash}")
                    .headers(headers)
                    .routeParam("username", username)
                    .routeParam("passwordHash", hashedPassword)
                    .asString();
            Boolean validLogin = Boolean.parseBoolean(validateUser.getBody());
            if(validLogin){
                HttpResponse<String> getUsers = Unirest.get("http://localhost:4567/usuarios/username/{username}")
                        .headers(headers)
                        .routeParam("username", username)
                        .asString();
                Session session=request.session(true);
                Usuario usuario = new Gson().fromJson(getUsers.getBody(), (Type) Usuario.class);
                session.attribute("usuario", usuario);
                String remember = request.queryParams("remember");
                if(remember != null){
                    response.cookie("usuario_id", usuario.getId(), 604800000);
                }
                response.redirect("/menu/1");
            }
            else{
                String warningText = "Usuario o contrasena incorrectos.";
                Map<String, Object> attributes = new HashMap<>();
                attributes.put("warningText", warningText);
                return getPlantilla(configuration, attributes, "login.ftl");
            }
            return "";
        });

        Spark.get("/register", (request, response) -> {
            String warningText = "";
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("warningText", warningText);
            return getPlantilla(configuration, attributes, "register.ftl");
        });

        Spark.post("/register", (request, response) -> {
            String nombre = request.queryParams("first_name") + " " + request.queryParams("last_name");
            String username = request.queryParams("username");
            String password = request.queryParams("password");
            String hashedPassword = DigestUtils.md5Hex(password);
            String confirmPassword = request.queryParams("confirm_password");


            HttpResponse<String> validatePassword = Unirest.get("http://localhost:4567/usuarios/confirmPassword/{confirmPassword}/{password}")
                    .headers(headers)
                    .routeParam("password", password)
                    .routeParam("confirmPassword", confirmPassword)
                    .asString();

            HttpResponse<String> validateUsername = Unirest.get("http://localhost:4567/usuarios/confirmUsername/{username}")
                    .headers(headers)
                    .routeParam("username", username)
                    .asString();

            Boolean validPassword = Boolean.parseBoolean(validatePassword.getBody());
            Boolean validUsername = Boolean.parseBoolean(validateUsername.getBody());

            if(validUsername && validPassword){
                String id = UUID.randomUUID().toString();
                Usuario usuario = new Usuario(username, nombre, hashedPassword, false);
                usuario.setId(id);
                Unirest.post("http://localhost:4567/usuarios/registrarUsuario")
                        .header("Content-Type", "application/json")
                        .body(usuario)
                        .asEmpty();
                Session session=request.session(true);
                session.attribute("usuario", usuario);
                response.redirect("/menu/1");
            }
            else if(!validUsername){
                String warningText = "El usuario ya existe";
                Map<String, Object> attributes = new HashMap<>();
                attributes.put("warningText", warningText);
                return getPlantilla(configuration, attributes, "register.ftl");
            }
            else if(!validPassword){
                String warningText = "Las contraseñas no coinciden";
                Map<String, Object> attributes = new HashMap<>();
                attributes.put("warningText", warningText);
                return getPlantilla(configuration, attributes, "register.ftl");
            }
            return "";
        });

        Spark.get("/disconnect", (request, response) -> {
            Session session=request.session(true);
            session.invalidate();
            response.removeCookie("usuario_id");
            response.redirect("/menu/1");
            return "";
        });

        before((request, response) -> {
            if(!request.pathInfo().equalsIgnoreCase("/requestToken")){
                HttpResponse<String> validateToken = Unirest.get("http://localhost:4567/validateToken")
                        .headers(headers)
                        .asString();
                Boolean validate = Boolean.parseBoolean(validateToken.getBody());
                if(!validate){
                    halt(401);
                }
            }
        });
    }

    public StringWriter getPlantilla(freemarker.template.Configuration configuration, Map<String, Object> model, String templatePath) throws IOException, TemplateException {
        Template plantillaPrincipal = configuration.getTemplate(templatePath);
        StringWriter writer = new StringWriter();
        plantillaPrincipal.process(model, writer);
        return writer;
    }
}
