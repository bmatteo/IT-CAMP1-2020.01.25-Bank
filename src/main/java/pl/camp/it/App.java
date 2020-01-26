package pl.camp.it;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.camp.it.config.SecondContext;
import pl.camp.it.gui.Gui;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(SecondContext.class);

        Gui mojeGui = context.getBean(Gui.class);

        mojeGui.run();
    }
}
