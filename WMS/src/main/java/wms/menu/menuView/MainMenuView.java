package wms.menu.menuView;

import java.util.Scanner;

public class MainMenuView {
    public void mainView()
    {
        Scanner sc=new Scanner(System.in);
        int choice;
        String menu= """
                        1. 발주
                        """;
        System.out.printf(menu);
        System.out.printf("메뉴를 입력해주세요 : ");
        choice=sc.nextInt();

        switch (choice)
        {
            case 1->new InboundManagementView().InboundManagementMenu();
        }
    }
}
