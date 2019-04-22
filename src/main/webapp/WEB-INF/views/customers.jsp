<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<section class="container">
	<div class="row">
		<c:forEach items="${customers}" var="customer">
			<div class="col-sm-6 col-md-3">
				<div class="thumbnail">
					<div class="caption">
						<h3>${customer.name}</h3>
						<p>${customer.customerId}</p>
						<p>${customer.noOfOrdersMade}</p>
						<p>Address: ${customer.address}</p>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
</section>
