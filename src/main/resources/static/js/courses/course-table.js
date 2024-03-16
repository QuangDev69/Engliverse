function toggleLessonList(courseId) {
    var list = document.getElementById(courseId);
    if (list) {
        list.classList.toggle('d-none');
        var button = list.nextElementSibling; // Giả định nút là phần tử kế tiếp
        button.textContent = button.textContent === 'Show All' ? 'Hide' : 'Show All';
    }
}
