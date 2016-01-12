<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html lang="no">
<head>
    <link rel="stylesheet" href="<c:url value="/resources/css/Hovedstilen.css"/>">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Min Side</title>
</head>

<body>
<header>
    <a href="Forside"><img id="logo" src="<c:url value="/resources/images/LogoTeam1.png"/>"></a>
    NTNU - Norges Teknisk-naturvitenskapelige Universitet · Study Easy © 2016<br>
    <nav class="dropdownmenu">
        <ul>
            <li><a href="Forside">Forside</a></li>
            <li><a href="Forside">Kontakt</a></li>
            <li><a href="#">Romvalg</a>
                <ul id="submenu">
                    <li><a href="VelgRom">Bestill rom</a></li>
                    <li><a href="FinnRom">Finn rom</a></li>
                </ul>
            </li>
            <li><a href="SokeSide">Søk</a></li>
            <li><a href="#">Min side</a>
                <ul id="submenu">
                    <li><a href="MinSide">Min informasjon</a></li>
                    <li><a href="loggUt">Logg ut</a></li>
                </ul>
        </ul>
    </nav>
</header>
<main>
    <form action="MinSide" id="formen" autocomplete="on" method="post" onsubmit="return checkEmail(this);">
        <fieldset style = "width:20%">
            <legend>Personopplysninger</legend>
            <dl>
                <form:form modelAttribute="bruker">
                <form:input type="password" placeholder="Gammelt passord" path="passord" style="width: 420px"/>
                <form:input type="password" placeholder="Nytt passord" path="passord" style="width: 420px"/>
                <form:input type="password" placeholder="Gjenta nytt passord" path="passord" style="width: 420px"/>
            </dl>
                

            <p><input type="submit" value="Endre passord" onclick="behandle()"></p>
            </form:form>
        </fieldset>


    </form>
</main>



<script>  
    window.onload = function(){
        document.getElementById('epost0').addEventListener('change', function() {
        var s = getElementById('epost1');
        s.value = document.getElementById('epost0');
    });
    };
 </script>   
<script>
    function elementSupportsAttribute(element,attribute) {
        return (attribute in document.createElement(element));
    }

    // Sjekker om et element har en klasse.
    function hasClass(element, cls) {
        return (' ' + element.className + ' ').indexOf(' ' + cls + ' ') > -1;
    }
    //Funksjon for å sjekke om emailene stemmer overens
    function checkEmail(formen) {
        if (formen.epost1.value != formen.epost2.value)
        {
            alert('E-postene samsvarer ikke!');
            return false;
        } else {
            return true;
        }
    }
    //Validerer utfyllingen
    function behandle(){

        var inputs = [document.getElementById('fornavn'),document.getElementById('etternavn'),
            document.getElementById('telefon'), document.getElementById('epost1'),
            document.getElementById('epost2'), document.getElementById('dato1')];

        var error;

        for(var i = 0; i<inputs.length; i++)
        {
            if(inputs[i].value == '')
            {
                error = 'Fullfør alle felt';
                alert(error);
                return false;
                break;
            }
        }


    }
</script>


<!--Footer-->
<div class="space"></div>
<!--Må være med for å funke i Chrome-->

<footer>
    <h4>NTNU - Norges Teknisk-naturvitenskapelige Universitet</h4>
    <a class="footerLenke" href="Forside">Hjem</a>
    ·
    <a class="footerLenke" href="MinSide">Min side</a>
    ·
    <a class="footerLenke" href="VelgRom">Bestill rom</a>
    ·
    <a class="footerLenke" href="Forside">Finn rom</a>
    ·
    <a class="footerLenke" href="SokeSide">Søk</a>
    ·
    <a class="footerLenke" href="Forside">Kontakt</a>
    <h4>·Study Easy © 2016·</h4>
</footer>


</body>
</html>