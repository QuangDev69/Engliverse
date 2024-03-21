// Khởi tạo trình soạn thảo Quill bằng jQuery
var quill = new Quill('#quill-editor', {
    theme: 'snow'
});

// Sử dụng jQuery để lắng nghe sự kiện submit của biểu mẫu
$('#form-add').submit(function(event) {
    // Ngăn chặn hành vi mặc định của sự kiện submit
    event.preventDefault();

    // Lấy nội dung của trình soạn thảo Quill bằng jQuery
    var content = $('#quill-editor .ql-editor').html();

    // Gán nội dung cho trường input bằng jQuery
    $('input[name=content]').val(content);

    // Tiến hành gửi biểu mẫu nếu cần
    this.submit();
});
