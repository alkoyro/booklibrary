function migrate(from, into)
{
    $(from +" option:selected").each(function()
    {
        var toAdd = $(this).clone();
        $(this).remove();
        $(into).append(toAdd);
    });



}

function selectAllItems()
{
    var result=new Array();
    $("#editUser\\:userPermissions\\:userPerm option").each(function(i)
    {
         result[i] =$(this).val();
    });
    $("#editUser\\:userPermissions\\:resultPermissions").val(result);
}


