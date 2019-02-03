<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css"> 

<title>Products</title>

</head>

<body>
	<section>
		<div class="jumbotron">
			<div class="container">
				<h1>Products</h1>
				<p>Add products </p>
			</div>
		</div>
	</section>
	
	<section class="container">
		<form:form method="POST" modelAttribute="newProduct" class="form-horizontal">
			<fieldset>
				<legend>Add new Prodcut</legend>
				
				<div class="form-group">
					<label class="control-label col-lg-2 col-lg-2" for="productId">Product Id</label>
					<div class="col-lg-10">
						<form:input id="productId" path="productId" type="text" class="form:input-large"/> 
					</div>
				</div>
				
				<div class="form-group"> 
                     <label class="control-label col-lg-2" 
                      for="description">Description</label> 
                     <div class="col-lg-10"> 
                        <form:textarea id="description" path="description" rows = "2"/> 
                     </div> 
                  </div> 
                  
				<div class="form-group"> 
                     <label class="control-label col-lg-2" 
                      for="discontinued">Discontinued</label> 
                    <div class="col-lg-10"> 
                        <form:checkbox  id="discontinued" path="discontinued"/> 
                     </div> 
                  </div>
				
				<div class="form-group"> 
                     <label class="control-label col-lg-2" 
                      for="condition">Condition</label> 
                     <div class="col-lg-10"> 
                        <form:radiobutton path="condition" value="New" />New  
                        <form:radiobutton path="condition" value="Old" />Old  
                        <form:radiobutton path="condition" value="Refurbished" />Refurbished 
                     </div> 
                  </div>
                  
                  <div class="form-group"> 
                     <div class="col-lg-offset-2 col-lg-10"> 
                        <input type="submit" id="btnAdd" class="btn 
                         btn-primary" value ="Add"/> 
                     </div> 
                  </div>
			</fieldset>
		</form:form>
	</section>
</body>

</head>
</html>