// $(document).ready(function() {
//     $('.fas.fa-camera').click(function() {
//         $('#image').click();
//     });
// });

document.querySelector('.fa-camera').addEventListener('click', function() {
    $('#avatarUpdateModal').modal('show');
});

document.getElementById('saveAvatarChange').addEventListener('click', function() {
    var file = document.getElementById('modalImage').files[0];
    if (file) {
        var reader = new FileReader();
        reader.onload = function(e) {
            document.querySelector('.course-image').src = e.target.result;
            $('#avatarUpdateModal').modal('hide');
        }
        reader.readAsDataURL(file);
    }
});
function previewFile() {
    var preview = document.getElementById('previewImage');
    var file    = document.getElementById('modalImage').files[0];
    var reader  = new FileReader();

    reader.onloadend = function () {
        preview.src = reader.result;
    }

    if (file) {
        reader.readAsDataURL(file);
    } else {
        preview.src = "";
    }
}
document.getElementById('saveAvatarChange').addEventListener('click', function() {
    // Submit form
    document.getElementById('userForm').submit();
});

$(document).ready(function(){
    $(".close, .btn-secondary").click(function(){
        $("#avatarUpdateModal").modal('hide');
        $("#editUserModal").modal('hide');

    });

    $("#editButton").click(function() {
        $("#editUserModal").modal('show');
    });

    // Đóng modal bằng nút đóng hoặc nút hủy
    $(".close, #cancelButton").click(function() {
        $("#editUserModal").modal('hide');
    });
});



