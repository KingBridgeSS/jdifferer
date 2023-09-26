package org.bridge;

import org.bridge.gui.MainFrame;

public class Main {
    private static void printBanner() {
        String bannerString = "     ██╗██████╗ ██╗███████╗███████╗███████╗██████╗ ███████╗██████╗ \n" +
                "     ██║██╔══██╗██║██╔════╝██╔════╝██╔════╝██╔══██╗██╔════╝██╔══██╗\n" +
                "     ██║██║  ██║██║█████╗  █████╗  █████╗  ██████╔╝█████╗  ██████╔╝\n" +
                "██   ██║██║  ██║██║██╔══╝  ██╔══╝  ██╔══╝  ██╔══██╗██╔══╝  ██╔══██╗\n" +
                "╚█████╔╝██████╔╝██║██║     ██║     ███████╗██║  ██║███████╗██║  ██║\n" +
                " ╚════╝ ╚═════╝ ╚═╝╚═╝     ╚═╝     ╚══════╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝\n" +
                "                                                                   \n" +
                "--- Powered By JDcore";
        System.out.println(bannerString);
    }

    public static void main(String[] args) {
        printBanner();
        new MainFrame().showGUI();
    }
}