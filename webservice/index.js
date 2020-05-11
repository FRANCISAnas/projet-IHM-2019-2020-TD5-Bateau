$(document).ready(function ($) {
    $.ajax({
        type: "GET",
        url: "api/incident.php",
        success: function (data) {
            let incidents = JSON.parse(data);
            incidents.forEach(function (incident) {
                $("#incidents").append(
                    '<div class="jumbotron">\n' +
                    '                <p> nature : ' + incident.nature + '</p>' +
                    '                <p> description : ' + incident.description + '</p>' +
                    '                <p> latitude :' + incident.latitude + '</p>' +
                    '                <p> longitude :' + incident.longitude + '</p>' +
                    '                <p> date :' + incident.date + '</p>' +
                    '<img src="images/' + incident.unique_id + '.JPG" alt="logo" height="40"/>' +
                    '</div>'
                );
            });
        },
        error: function () {
            alert("Incident loading failed");
        }
    });
    $('#submit').click(function () {
        $.ajax({
            type: 'POST',
            url: 'api/incident.php',
            dataType: 'json',
            data: {
                nature: $('#nature').val(),
                description: $('#description').val()
            },
            success: function (data) {
                location.reload();
            },
            error: function () {
                alert("Incident creation failed");
            }
        });
    });
});
