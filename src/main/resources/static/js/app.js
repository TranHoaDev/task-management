function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            $('#img')
                .attr('src', e.target.result);
        };
        reader.readAsDataURL(input.files[0]);
    }
}

$(document).ready(() => {
    const deleteBtn = $("#delete-btn")
    deleteBtn.click(() => addClickEvent())
})

function addClickEvent(){
    $(".popup").css("display", "block")
    $("#cancel-delete").click(() => {
        $(".popup").css("display", "none")
    })
}