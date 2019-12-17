<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>The ERP Blog</title>

    <!-- Bootstrap core CSS -->
    <!--<link href="resources/resources.publico/startbootstrap-blog-home-gh-pages/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">-->

    <!-- Custom styles for this template -->
    <!--<link href="resources/resources.publico/startbootstrap-blog-home-gh-pages/css/blog-home.css" rel="stylesheet">-->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="../js/qrcode.min.js"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <!-- microlink -->
    <script src="https://cdn.jsdelivr.net/combine/npm/react@16/umd/react.production.min.js,npm/react-dom@16/umd/react-dom.production.min.js,npm/styled-components@4/dist/styled-components.min.js,npm/@microlink/mql@latest/dist/mql.min.js,npm/@microlink/vanilla@latest/dist/microlink.min.js"></script><script src="https://cdn.jsdelivr.net/combine/npm/react@16/umd/react.production.min.js,npm/react-dom@16/umd/react-dom.production.min.js,npm/styled-components@4/dist/styled-components.min.js,npm/@microlink/mql@latest/dist/mql.min.js,npm/@microlink/vanilla@latest/dist/microlink.min.js"></script>

    <style>
        .microlink-cards{
            max-width: 20 px;
        }
    </style>


</head>

<body>

<!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <div class="container">
        <a class="navbar-brand" href="#">The ERP Url Shorter</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="/menu/1">Home
                        <span class="sr-only">(current)</span>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">About</a>
                </li>
                <#if loggedUser?exists>
                    <li class="nav-item">
                        <a class="nav-link" href="#">${loggedUser.username}</a>
                    </li>
                </#if>
                <#if loggedUser?exists && loggedUser.administrador == true>
                    <li class="nav-item">
                        <a class="nav-link" href="/administradores">Autores</a>
                    </li>
                </#if>
                <#if loggedUser?exists>
                    <li class="nav-item">
                        <a class="nav-link" href="/disconnect">Desconectar</a>
                    </li>
                <#else>
                    <li class="nav-item">
                        <a class="nav-link" href="/login">Login</a>
                    </li>
                </#if>
            </ul>
        </div>
    </div>
</nav>

<!-- Page Content -->
<div class="container">

    <div class="row">

        <!-- Blog Entries Column -->
        <div class="col-md-8">
            <br><br>
            <h1 class="my-4">Page Heading
            </h1>

            <h2 class="my-4">Posts</h2>

            <!-- Post Creation Form -->
            <form method="post" action="/createUrl" class="form-inline">
                <div class="form-group">
                    <input class="form-control" name="originalUrl" placeholder="URL" type="text">
                </div>
                <button id="postPost" type="submit" class="btn btn-primary">Shorten!</button>
            </form>
            <br><br>

            <div id="qr">
                <div id="qrcode"></div>
            </div>

            <!--Tablas de links -->
            <div class="row">
                <div class="col-md-8">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>Link</th>
                            <th>Created By</th>
                            <th>Accion</th>
                            <th>Analisis</th>
                        </tr>
                        </thead>
                        <tbody>

                        <#if links?size != 0>
                            <#list links as link>
                                <tr>
                                    <td><a href="http://${link.urlOriginal}" class="link-previews microlink-cards">${link.urlOriginal}</a>
                                    <a href="../shorty/${link.urlBase62}">/shorty.com/${link.urlBase62}</a></td>
                                    <#if link.creador?exists>
                                        <td>${link.creador.username}</td>
                                    <#else>
                                        <td>Anonimo</td>
                                    </#if>
                                    <td>
                                        <a href="/eliminarUrl/${link.urlIndexada}">Eliminar</a>
                                    </td>
                                    <#--                                <td><a id="analisis" class="btn btn-primary" name="${link.urlBase62?substring(12)}">QR</a></td>-->
                                    <td>
                                        <a href="/stats/${link.urlIndexada}">Stats</a>
                                    </td>
                                    <td>
                                        <div id="qrcode${link.urlBase62}"></div>
                                        <script type="text/javascript">
                                            new QRCode(document.getElementById("qrcode${link.urlBase62}"), "${link.urlOriginal}");
                                            var qrcode = new QRCode("test", {
                                                text: "/shorty.com/${link.urlBase62}",
                                                width: 128,
                                                height: 128,
                                                colorDark : "#000000",
                                                colorLight : "#ffffff",
                                                correctLevel : QRCode.CorrectLevel.H
                                            });
                                        </script>
                                    </td>
                                </tr>
                            </#list>
                        </#if>

                        </tbody>
                    </table>
                </div>
            </div>


            <!-- Blog Post -->
            <div class="card mb-4">
            </div>
            <!-- Pagination -->
            <ul class="pagination justify-content-center mb-4">
                <li id="listOlder">
                    <a id="olderButton" class="page-link" href="#">&larr; Older</a>
                </li>
                <li id="listNewer">
                    <a id="newerButton" class="page-link" href="#">Newer &rarr;</a>
                </li>
            </ul>

        </div>

        <!-- Sidebar Widgets Column -->
        <div class="col-md-4">
            <br><br>
            <!-- Search Widget -->
            <div class="card my-4">
                <h5 class="card-header">Search</h5>
                <div class="card-body">
                    <div class="input-group">
                        <input id="buscaTag" name="buscaTag" type="text" class="form-control" placeholder="Buscar por tag...">
                        <span class="input-group-btn">
                <button id="tagBusqueda" class="btn btn-secondary" type="button">Buscar</button>
              </span>
                    </div>
                </div>
            </div>

        </div>

    </div>
    <!-- /.row -->

</div>
<!-- /.container -->

<!-- Footer -->
<footer class="py-5 bg-dark">
    <div class="container">
        <p class="m-0 text-center text-white">Copyright &copy; Your Website 2019</p>
    </div>
    <!-- /.container -->
</footer>

<#--<script type="text/javascript">-->
<#--    $(document).ready(function(){-->
<#--        $('#analisis').click(function () {-->
<#--            var elem = document.getElementById('qrcode');-->
<#--            elem.parentNode.removeChild(elem);-->
<#--            var html = document.createElement('div');-->
<#--            html.id = 'qrcode';-->
<#--            document.getElementById('qr').appendChild(html);-->
<#--            var url = ;-->
<#--            new QRCode(document.getElementById("qrcode"), "/shorty.com/" + url);-->

<#--            var qrcode = new QRCode("test", {-->
<#--                text: "test",-->
<#--                width: 128,-->
<#--                height: 128,-->
<#--                colorDark: "#000000",-->
<#--                colorLight: "#ffffff",-->
<#--                correctLevel: QRCode.CorrectLevel.H,-->
<#--                margin: 20-->
<#--            });-->
<#--        });-->
<#--    });-->
<#--</script>-->

<script type="text/javascript">
    $(document).ready(function() {
            var pageNumber = ${pageNumber};
            var sizeAllLinks = ${sizeAllLinks};
            var ruta;
            if (pageNumber == 1) {
                $('#listOlder').addClass("page-item disabled")
            } else {
                $('#listOlder').addClass("page-item");
            }
            if (sizeAllLinks < 5) {
                $('#listNewer').addClass("page-item disabled");
            } else {
                $('#listNewer').addClass("page-item");
            }
            $('#newerButton').on('click', function () {
                <#if links?exists>
                ruta = "/menu/" + ++pageNumber;
                </#if>
                console.log(ruta);
                document.location.href = ruta.toString();
            });
            $('#olderButton').on('click', function () {
                <#if links?exists>
                ruta = "/menu/" + --pageNumber;
                </#if>
                console.log(ruta);
                document.location.href = ruta.toString();
            });
        $('#eliminarUrl').on('click', function(){
            var ruta = "/eliminarUrl/" + $(this).attr('data-url');
            console.log(ruta);
            document.location.href = ruta.toString();
        })
    });
</script>


<script>
    document.addEventListener('DOMContentLoaded', function (event) {
        microlink('.link-previews', {
            size: 'small',
        })
    })
</script>

</body>