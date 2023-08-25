$(document).ready(function () {

    $('.nBtn, .table .eBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
            $.get(href, function (editUser, status) {
                $('.EditForm #id').val(editUser.id);
                $('.EditForm #firstName').val(editUser.firstName);
                $('.EditForm #lastName').val(editUser.lastName);
                $('.EditForm #age').val(editUser.age);
                $('.EditForm #email').val(editUser.email);
                $('.EditForm #username').val(editUser.username);
                $('.EditForm #password').val();
                $('.EditForm #role').val(editUser.role);
            })
            ;$('.EditForm #exampleModal').modal();
    });

    $('.table .delBtn').click(function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $.get(href,function (delUser,status){
            $('.DeleteForm #DelId').val(delUser.id);
            $('.DeleteForm #DelFirstName').val(delUser.firstName);
            $('.DeleteForm #DelLastName').val(delUser.lastName);
            $('.DeleteForm #DelAge').val(delUser.age);
            $('.DeleteForm #DelEmail').val(delUser.email);
            $('.DeleteForm #DelPassword').val(delUser.password);
            var delRolesSelect = $('#DelRoles');
            delRolesSelect.empty();
            for (var i = 0; i < delUser.roles.length; i++) {
                var role = delUser.roles[i];
                var option = $('<option></option>').text(role.name.replace('ROLE_',''));
                delRolesSelect.append(option);
            }
        });

        $('#DeleteModal').modal();
    });
});