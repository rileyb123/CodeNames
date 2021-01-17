package code.ActionListen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuitActionListener implements ActionListener{
	@Override
    public void actionPerformed(ActionEvent e) {
		//Exits Application
        System.exit(0);
    }
}
