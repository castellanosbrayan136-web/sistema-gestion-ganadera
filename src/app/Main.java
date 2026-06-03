package app;

//@autor Brayan C

import com.formdev.flatlaf.intellijthemes.FlatGradiantoDeepOceanIJTheme;
import vista.ScreenManager;

public class Main {
    public static void main(String[] args) {
        FlatGradiantoDeepOceanIJTheme.setup();
        ScreenManager.abrirVistaAutenticacion();
    }
}