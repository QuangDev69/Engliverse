/*<![CDATA[*/
$(document).ready(function() {
    var successMessage = /*[[${success}]]*/ null;
    if (successMessage != null) {
        toastr.success(successMessage);
    }

    var errorMessage = /*[[${error}]]*/ null;
    if (errorMessage != null) {
        toastr.error(errorMessage);
    }

    $('#goToPageForm').on('submit', function(e) {
        var page = parseInt($('#goToPage').val());
        if (page > 1) {
            $('#goToPage').val(page - 1);
        } else {
            $('#goToPage').val(0);
        }
    });
});
/*]]>*/