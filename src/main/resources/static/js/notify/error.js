/*<![CDATA[*/
$(document).ready(function() {
    var errorMessage = [[${error}]];
    if (errorMessage != null && errorMessage !== '') {
        toastr.error(errorMessage);
    }
});
/*]]>*/