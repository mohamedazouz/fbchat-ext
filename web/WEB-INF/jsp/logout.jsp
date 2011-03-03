<div id="fb-root"></div>
<script src="http://connect.facebook.net/en_US/all.js"></script>
<script>
    FB.init({appId  : '172430629459688',status : true,cookie : true,xfbml  : true });
    FB.getLoginStatus(function(response) {
        if (response.session) {
            FB.logout();
        }
    });
</script>

