$(document).ready(function () {

    $("#search-form").submit(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        fire_ajax_submit();
    });

     $(".btn.btn-primary.btn-sm").click(function (event) {
            //stop submit the form, we will post it manually.
            event.preventDefault();
            //console.log(this.getAttribute('data-type'));
            var dataType = $(this).attr("data-type");
            var blog = $(this).attr("blog");

            console.log(dataType);
            $.ajax({
                    url: '/remove/' + blog + '/'+ dataType,
                    type: 'DELETE',
                    success: function(result) {
                        console.log("Lol_kek: ", dataType);
                        $( "div.gallery#" + dataType).hide( "slow",);

                    }
                });
        });
});




function fire_ajax_submit() {

    var search = {}
    search["username"] = $("#username").val();
    //search["email"] = $("#email").val();

    $("#btn-search").prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "api/search",
        data: JSON.stringify(search),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

            var json = "<h4>Ajax Response</h4><pre>"
                + JSON.stringify(data, null, 4) + "</pre>";
            $('#feedback').html(json);

            console.log("SUCCESS : ", data);
            $("#btn-search").prop("disabled", false);

            $( "#myAjaxContent" ).hide( "slow",);

        },
        error: function (e) {

            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);

            console.log("ERROR : ", e);
            $("#btn-search").prop("disabled", false);

        }
    });

}