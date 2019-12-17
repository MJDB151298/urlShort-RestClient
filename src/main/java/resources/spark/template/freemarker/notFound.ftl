<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>The ERP Blog</title>

    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
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

<div class="container">
    <br><br><br><br><br><br>
    <div class="row">
        <div class="col-md-12">
            <div class="d-flex justify-content-center align-items-center" id="main">
                <h1 class="mr-3 pr-3 align-top border-right inline-block align-content-center">404</h1>
                <div class="inline-block align-middle">
                    <h2 class="font-weight-normal lead" id="desc">El url no existe.</h2>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
