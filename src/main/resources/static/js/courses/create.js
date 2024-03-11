    $(document).ready(function() {
    $('form').submit(function(e) {
        // Lấy giá trị của các trường input
        var courseName = $('#courseName').val();
        var courseDes = $('#courseDes').val();
        var topicCheckboxes = $('input[name="topicIds"]:checked');
        var levelCheckboxes = $('input[name="levelId"]:checked');

        // Kiểm tra nếu có bất kỳ trường nào bị rỗng hoặc null
        if (!courseName || !courseDes  || topicCheckboxes.length === 0 || levelCheckboxes.length === 0) {
            // Hiển thị thông báo lỗi
            alert('Please fill in all required fields.');
            // Ngăn chặn gửi biểu mẫu
            e.preventDefault();
        }
        if (topicCheckboxes.length > 2) {
            alert('Please select no more than 2 topics.');
            e.preventDefault();
        }
    });
});
