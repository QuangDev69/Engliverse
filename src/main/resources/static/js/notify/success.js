$(document).ready(function() {
    toastr.options = {
        closeButton: true, // Bật nút đóng (X) cho thông báo
        progressBar: true, // Hiển thị thanh tiến trình (tuỳ chọn)
        positionClass: "toast-top-right", // Vị trí hiển thị thông báo
        showDuration: "300", // Thời gian hiệu ứng hiển thị (ms)
        hideDuration: "1000", // Thời gian hiệu ứng ẩn (ms)
        timeOut: "3000", // Thời gian tồn tại của thông báo trước khi tự động ẩn (ms)
        extendedTimeOut: "1000", // Thời gian tồn tại thêm sau khi người dùng di chuột vào thông báo
        showEasing: "swing", // Hiệu ứng easing khi hiển thị
        hideEasing: "linear", // Hiệu ứng easing khi ẩn
        showMethod: "fadeIn", // Phương thức hiển thị
        hideMethod: "fadeOut" // Phương thức ẩn
    };
    if (successMessage != null) {
        toastr.success(successMessage);
    }

});
