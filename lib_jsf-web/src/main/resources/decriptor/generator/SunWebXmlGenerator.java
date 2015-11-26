package decriptor.generator;

import by.es.realm.entity.predefined.Permission;

import java.util.*;

/**
 * Created by Alexey.Koyro
 * <p/>
 * generates security role mapping
 */
public class SunWebXmlGenerator
{
    private static Map<Permission, Set<Permission>> roleGroupMap = new HashMap<Permission, Set<Permission>>();

    static
    {
        List<Permission> userTypes = Arrays.asList(Permission.ADD_BOOK);
        roleGroupMap.put(Permission.ADD_BOOK, new HashSet<Permission>(userTypes));

        userTypes = Arrays.asList(Permission.EDIT_BOOK);
        roleGroupMap.put(Permission.EDIT_BOOK, new HashSet<Permission>(userTypes));

        userTypes = Arrays.asList(Permission.EDIT_USER);
        roleGroupMap.put(Permission.EDIT_USER, new HashSet<Permission>(userTypes));

        userTypes = Arrays.asList(Permission.EDIT_MANAGER);
        roleGroupMap.put(Permission.EDIT_MANAGER, new HashSet<Permission>(userTypes));

        userTypes = Arrays.asList(Permission.BLOCK_USER);
        roleGroupMap.put(Permission.BLOCK_USER, new HashSet<Permission>(userTypes));

        userTypes = Arrays.asList(Permission.GIVE_DISCOUNT);
        roleGroupMap.put(Permission.GIVE_DISCOUNT, new HashSet<Permission>(userTypes));

        userTypes = Arrays.asList(Permission.PURCHASE_HISTORY);
        roleGroupMap.put(Permission.PURCHASE_HISTORY, new HashSet<Permission>(userTypes));

        userTypes = Arrays.asList(Permission.UPLOAD_DIGITAL_BOOK);
        roleGroupMap.put(Permission.UPLOAD_DIGITAL_BOOK, new HashSet<Permission>(userTypes));

        userTypes = Arrays.asList(Permission.DOWNLOAD_DIGITAL_BOOK);
        roleGroupMap.put(Permission.DOWNLOAD_DIGITAL_BOOK, new HashSet<Permission>(userTypes));

        userTypes = Arrays.asList(Permission.LEAVE_COMMENT);
        roleGroupMap.put(Permission.LEAVE_COMMENT, new HashSet<Permission>(userTypes));

        userTypes = Arrays.asList(Permission.ADD_TO_CART);
        roleGroupMap.put(Permission.ADD_TO_CART, new HashSet<Permission>(userTypes));

        userTypes = Arrays.asList(Permission.CREATE_USER);
        roleGroupMap.put(Permission.CREATE_USER, new HashSet<Permission>(userTypes));

        userTypes = Arrays.asList(Permission.LOAN_DEBTS);
        roleGroupMap.put(Permission.LOAN_DEBTS, new HashSet<Permission>(userTypes));

        userTypes = Arrays.asList(Permission.LOGIN_HISTORY);
        roleGroupMap.put(Permission.LOGIN_HISTORY, new HashSet<Permission>(userTypes));

        userTypes = Arrays.asList(Permission.SERVER_STATS);
        roleGroupMap.put(Permission.SERVER_STATS, new HashSet<Permission>(userTypes));

        userTypes = Arrays.asList(Permission.LOOK_INTO_CART);
        roleGroupMap.put(Permission.LOOK_INTO_CART, new HashSet<Permission>(userTypes));
    }


    public static void main(String[] args)
    {
        for (Permission role : roleGroupMap.keySet())
        {
            System.out.println("<security-role-mapping>");
            System.out.println("<role-name>" + role.name() + "</role-name>");
            for (Permission userType : roleGroupMap.get(role))
            {
                System.out.println("<group-name>" + userType.name() + "</group-name>");
            }
            System.out.println("</security-role-mapping>");
            System.out.println("\n");
        }

        System.out.println("------------------------------------");

        for (Permission role : roleGroupMap.keySet())
        {
            System.out.println("<security-role>");
            System.out.println("<role-name>" + role.name() + "</role-name>");
            System.out.println("</security-role>");
            System.out.println("\n");
        }
    }
}
