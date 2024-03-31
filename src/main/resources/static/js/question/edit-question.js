var quill = new Quill('#quill-editor', {
    theme: 'snow'
});

$(document).ready(function () {
    // Sử dụng class 'edit-btn' để mở modal chỉnh sửa
    $('.edit-btn').click(function () {
        var questionId = $(this).data('question-id');
        $('#editQuestionModal' + questionId).modal('show');
    });

    // Bắt sự kiện submit cho tất cả các form trong modal
    $('.modal').on('submit', 'form', function (event) {
        event.preventDefault(); // Ngăn chặn hành động submit mặc định

        // Cập nhật giá trị cho input ẩn dựa trên trạng thái của checkbox
        $(this).find('.form-check-input[type="checkbox"]').each(function () {
            var hiddenInput = $(this).closest('.mb-3').find('input[type="hidden"][name="' + $(this).attr('name') + '"]');
            if ($(this).is(':checked')) {
                hiddenInput.val('true');
            } else {
                hiddenInput.val('false');
            }
        });

        // Tiếp tục gửi form
        this.submit(); // Hoặc bạn có thể sử dụng AJAX tại đây để gửi form mà không cần reload trang
    });

    // Cập nhật nội dung từ Quill vào input ẩn khi form bài học được submit
    $('#update-lesson-form').submit(function (event) {
        event.preventDefault();
        var content = $('input[name="content"]');
        content.val(quill.root.innerHTML);
        this.submit();
    });

    // Đóng modal khi nhấn nút đóng
    $("#formClose,.close, .btn-secondary").click(function () {
        $(".modal").modal('hide');
    });
});
