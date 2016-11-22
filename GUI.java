import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class BlackJackGUI extends JFrame{

    /**
     *
     */
    private static final long serialVersionUID = 1L;




    public boolean idkYet;

    private GridBagConstraints gbcDealer = new GridBagConstraints();
    private GridBagConstraints gbcPlayer = new GridBagConstraints();
    private GridBagConstraints gbc = new GridBagConstraints();


    private JFrame canvas = new JFrame();

    private JPanel mainPanel = new JPanel(new GridBagLayout());

    private JPanel jpDealer = new JPanel(new GridBagLayout());
    private JPanel jpDealerLeft = new JPanel(new GridBagLayout());
    private JLabel jlDealerName = new JLabel("Dealer");
    private JLabel jlDealerCards = new JLabel("Cards");
    private JPanel jpDealerRight = new JPanel(new GridBagLayout());
    private JLabel jlShuffleString = new JLabel("Until Shuffle");
    private JLabel jlShuffleAmount = new JLabel("Amount");
    private JLabel jlChipsString = new JLabel("Chips");
    private JLabel jlChipsCount = new JLabel("Count");



    private JPanel jpPlayer = new JPanel(new GridBagLayout());
    private JPanel jpPlayerLeft = new JPanel(new GridBagLayout());
    private JLabel jlPlayerName = new JLabel("Player");
    private JLabel jlPlayerCards = new JLabel("Cards");
    private JPanel jpPlayerRight = new JPanel(new GridBagLayout());
    private JLabel jlBetString = new JLabel("Bet");
    private JLabel jlBetAmount = new JLabel("Amount");

    private JPanel jpButtons = new JPanel(new GridBagLayout());
    private JButton jbtHit = new JButton("Hit");
    private JButton jbtStay = new JButton("Stay");
    private JButton jbtSplit = new JButton("Split");
    private JButton jbtDouble = new JButton("Double");
    private JButton jbtIncreaseBet = new JButton("Increase Bet");
    private JButton jbtDecreaseBet = new JButton("Decrease Bet");

    private JPanel jpEmpty = new JPanel();
    private JPanel jpBottom = new JPanel();
    private JButton jbtContinue = new JButton("Hello");
    private JLabel jlInfo = new JLabel("Hello");

    public BlackJackGUI(){

        idkYet = false;

        gbc.insets = new Insets(15,15,15,15);
        gbcDealer.insets = new Insets(0,10,0,10);
        gbcPlayer.insets = new Insets(5,5,5,5);


        canvas.setDefaultCloseOperation(EXIT_ON_CLOSE);
        canvas.setSize(400,600);
        canvas.setVisible(true);

        gbcDealer.gridy = 0;
        jpDealerLeft.add(jlDealerName,gbcDealer);
        gbcDealer.gridy = 1;
        jpDealerLeft.add(jlDealerCards,gbcDealer);

        gbcDealer.gridy = 0;
        jpDealerRight.add(jlShuffleAmount,gbcDealer);
        jpDealerRight.add(jlShuffleString,gbcDealer);
        gbcDealer.gridy = 1;
        jpDealerRight.add(jlChipsCount,gbcDealer);
        jpDealerRight.add(jlChipsString,gbcDealer);


        jpDealer.add(jpDealerLeft);
        jpDealer.add(jpEmpty);
        jpDealer.add(jpDealerRight);

        gbcPlayer.gridy = 0;
        jpPlayerLeft.add(jlPlayerName,gbcPlayer);
        gbcPlayer.gridy = 1;
        jpPlayerLeft.add(jlPlayerCards,gbcPlayer);


        gbcPlayer.gridy = 0;
        jpPlayerRight.add(jlBetAmount,gbcPlayer);
        gbcPlayer.gridy = 0;
        jpPlayerRight.add(jlBetString,gbcPlayer);


        jpPlayer.add(jpPlayerLeft);
        jpPlayer.add(jpPlayerRight);

        gbc.gridy = 0;
        jpButtons.add(jbtHit,gbc);
        gbc.gridy = 0;
        jpButtons.add(jbtStay,gbc);
        gbc.gridy = 1;
        jpButtons.add(jbtSplit,gbc);
        gbc.gridy = 1;
        jpButtons.add(jbtDouble,gbc);
        gbc.gridy = 0;
        jpButtons.add(jbtIncreaseBet,gbc);
        gbc.gridy = 1;
        jpButtons.add(jbtDecreaseBet,gbc);

        gbc.gridy = 0;
        mainPanel.add(jpDealer,gbc);
        gbc.gridy = 1;
        mainPanel.add(jpPlayer,gbc);
        gbc.gridy = 2;
        mainPanel.add(jpButtons, gbc);

        mainPanel.setBackground(Color.GREEN);

        canvas.add(mainPanel);

        jbtHit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                idkYet = true;
            }
        });
    }

    public void setChips(String inChips){
        jlChipsCount.setText(inChips);
    }

}
