$(document).ready(function () {
    var optionsIndex = 0; // Bắt đầu từ 0 vì không có Option mặc định nào

    $('#addOption').click(function () {
        var newOption = '<div id="option' + optionsIndex + '" class="mb-3">' +
            '<input type="text" name="options[' + optionsIndex + '].content" required class="form-control me-2"/>' +
            '<input type="checkbox" name="options[' + optionsIndex + '].isCorrect" value="true" class="me-2"/> Is Correct?' +
            '<input type="hidden" name="options[' + optionsIndex + '].isCorrect" value="false"/>' +
            '<button type="button" onclick="removeOption(' + optionsIndex + ')" class="btn btn-danger btn-sm">Remove</button>' +
            '</div>';
        $('#optionsContainer').append(newOption);
        optionsIndex++;
    });
});

function removeOption(index) {
    $('#option' + index).remove();
    // Cân nhắc giảm optionsIndex nếu cần
}
