document.getElementById("teacherBtn").addEventListener("click", function() {
    window.open(
        '/courseDetail/teacherProfile?id=${detail.id}',
        '_blank',
        `width=500,height=800,left=790,top=90,resizable=yes,scrollbars=yes`
    );
});
