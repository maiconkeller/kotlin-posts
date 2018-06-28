package ui;

import business.PostBusiness;
import entity.PostEntity;

import javax.swing.*;

public class PostDetailForm extends JFrame {
    private JLabel lblTitulo;
    private JPanel rootPanel;
    private JLabel lblBody;

    private PostBusiness mPostBusiness = new PostBusiness();

    public PostDetailForm(int id) {
        super();

        this.loadPost(id);
        
        this.setContentPane(rootPanel);
        this.setSize(500, 250);
        this.setVisible(true);

    }

    private void loadPost(int id) {
        PostEntity entity = this.mPostBusiness.getSinglePost(id);

        this.lblTitulo.setText("<html>" + entity.getTitle() + "</html>");
        this.lblBody.setText("<html>" + entity.getBody() + "</html>");
    }
}
