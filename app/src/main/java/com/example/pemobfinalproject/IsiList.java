package com.example.pemobfinalproject;

import java.util.ArrayList;

public class IsiList extends ArrayList <ListAccountPage> {
    private static int[] icon = {
            R.drawable.usernavbar,
            R.drawable.global,
            R.drawable.privacy,
            R.drawable.term_of_service,
            R.drawable.star,
            R.drawable.information
    };

    private static String[] iconTitle = {
            "Profile",
            "Languange",
            "Privacy Policy",
            "Terms of Service",
            "Rate Gofit app",
            "Help"
    };

    static ArrayList<ListAccountPage> getList() {
        ArrayList<ListAccountPage> listAccountPages = new ArrayList<>();
        for (int position = 0; position < iconTitle.length; position++) {
            ListAccountPage listAccountPage = new ListAccountPage();
            listAccountPage.setIcon(icon[position]);
            listAccountPage.setListData(iconTitle[position]);

            listAccountPages.add(listAccountPage);
        }

        return listAccountPages;
    }
}
