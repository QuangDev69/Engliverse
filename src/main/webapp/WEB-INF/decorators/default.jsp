<!DOCTYPE html>
<html>
<head>
    <title><sitemesh:write property='title'/></title>
    <sitemesh:write property='head'/>
    
    <style>
    	.header {
    		text-align: center;
    	}
    </style>
    
</head>
<body>
    <header>
        <h1 class="header">Header </h1>
    </header>

    <section>
        <sitemesh:write property='body'/>
    </section>

    <footer>
        <h1 class="header">Footer</h1>
    </footer>
</body>
</html>
