function toggleLessonList(courseId) {
    var list = document.getElementById(courseId);
    if (list) {
        list.classList.toggle('d-none');
        var button = list.nextElementSibling; // Giả định nút là phần tử kế tiếp
        button.textContent = button.textContent === 'Show All' ? 'Hide' : 'Show All';
    }
}
    $(document).ready(function() {
    $('td[data-level]').each(function() {
        var level = $(this).data('level');
        switch(level) {
            case 'Advanced':
                $(this).addClass('level-advance');
                break;
            case 'Intermediate':
                $(this).addClass('level-intermediate');
                break;
            case 'Basic':
                $(this).addClass('level-basic');
                break;
            default:
                console.log('Unknown level:', level);
        }
    });
});
