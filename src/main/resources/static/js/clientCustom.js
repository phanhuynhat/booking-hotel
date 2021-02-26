// khai bao ajax


$(document).ready(function () {
    $('#loopkUpFrom').submit(function (event) {
        event.preventDefault();
        $.ajax(
            {
                context: this, //trong pham vi event hanlder this chinh la phan tu dang kich hoat su kien
                url: '/lookup',
                type: 'POST',
                data: $(this).serialize(),
                success: function (result) {
                    if (result == "not found") {
                        $('#not-found-error').html('Mã đặt phòng không tồn tại trong hệ thống!')
                    } else {
                        this.submit();
                    }

                }
            }
        )
    });

})