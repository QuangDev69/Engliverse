
    $(document).ready(function() {
    $('form').submit(function(e) {
        var topicCheckboxes = $('input[name="topicIds"]:checked');
        if (topicCheckboxes.length > 2) {
            alert('Please select no more than 2 topics.');
            e.preventDefault();
        }
    });
});
