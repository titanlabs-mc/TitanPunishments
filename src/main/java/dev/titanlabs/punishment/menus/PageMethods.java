package dev.titanlabs.punishment.menus;

public interface PageMethods {

    void nextPage(Runnable runnable);

    void previousPage(Runnable runnable);

    int getPage();
}
