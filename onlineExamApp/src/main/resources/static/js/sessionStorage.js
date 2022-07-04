 	window.onload = function() {

        // If sessionStorage is storing default values (ex. name), exit the function and do not restore data
        if (sessionStorage.getItem('name') == "name") {
            return;
        }

        // If values are not blank, restore them to the fields
        var name = sessionStorage.getItem('name');
        if (name !== null) $('#name').val(name);

        var start = sessionStorage.getItem('start');
        if (start !== null) $('#start').val(start);

        var end = sessionStorage.getItem('end');
        if (end!== null) $('#end').val(end);

    }

    // Before refreshing the page, save the form data to sessionStorage
    window.onbeforeunload = function() {
        sessionStorage.setItem("name", $('#name').val());
        sessionStorage.setItem("start", $('#start').val());
        sessionStorage.setItem("end", $('#end').val());
    }

	