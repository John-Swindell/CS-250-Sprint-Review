import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Desktop; // Keep this single import for Desktop

public class SlideShow extends JFrame {

    // Added default serialVersionUID to potentially resolve a compiler warning
    private static final long serialVersionUID = 1L;

	//Declare Variables
    private JPanel slidePane = new JPanel();
    private JPanel textPane = new JPanel();
    private JPanel buttonPane = new JPanel();
    private JPanel southPane = new JPanel();
    private CardLayout card = new CardLayout();
    private CardLayout cardText = new CardLayout();
    private JButton btnPrev = new JButton();
    private JButton btnNext = new JButton();

    private List<Slide> slides = Arrays.asList(
    	    new Slide(
    	        "<font size='10' color='yellow'>#1 Sydney, Australia.</font> <br><font size='6' color='yellow'>Sydney is renowned for its stunning harbor, iconic landmarks like the Sydney Opera House, and beautiful beaches.</font> <br>",
    	        "/resources/oneoftheplaces.JPG",
    	        "https://www.sydney.com/"
    	    ),
    	    new Slide(
    	        "<font size='10' color='green'>#2 Machu Picchu, Peru.</font> <br><font size='6' color='green'>An ancient Incan citadel set high in the Andes Mountains, offering breathtaking views and rich history.</font> <br>",
    	        "/resources/machupicchu.jpg",
    	        "https://www.peru.travel/es"
    	    ),
    	    new Slide(
    	        "<font size='10' color='blue'>#3 Santorini, Greece.</font> <br><font size='6' color='blue'>Famous for its white-washed buildings with blue domes overlooking the Aegean Sea, offering stunning sunsets.</font> <br>",
    	        "/resources/greecetop.JPG",
    	        "https://www.visitgreece.gr/"
    	    ),
    	    new Slide(
    	        "<font size='10' color='teal'>#4 Great Wall of China.</font> <br><font size='6' color='teal'>An ancient series of fortifications stretching over 13,000 miles, showcasing China's historical grandeur.</font> <br>",
    	        "/resources/Great-Wall-of-China.jpg",
    	        "https://great-wall-china.org/en/"
    	    ),
    	    new Slide(
    	        "<font size='10' color='orange'>#5 Serengeti National Park, Tanzania.</font> <br><font size='6' color='orange'>A vast savannah teeming with wildlife, famous for the migration of over 1.5 million wildebeest and hundreds of thousands of zebras.</font> <br>",
    	        "/resources/TravelWideFlights.jpg",
    	        "https://www.tanzaniatourism.go.tz/destination/serengeti/"
    	    )
    	);


	/**
	 * Create the application.
	 */
	public SlideShow() throws HeadlessException {
		initComponent();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initComponent() {
		//Initialize variables to empty objects
        textPane.setBackground(Color.BLACK);
        textPane.setBounds(5, 470, 790, 50);
        textPane.setVisible(true);
        southPane.setLayout(new BoxLayout(southPane, BoxLayout.Y_AXIS));
        Color blackBackgroundColor = Color.BLACK;

		//Setup frame attributes
		setSize(800, 600);
		setLocationRelativeTo(null);
		setTitle("Top 5 Relaxing Destinations For Emotional Detox and Wellness "
				+ " by "
				+ "John Swindell");
		getContentPane().setLayout(new BorderLayout(10, 50));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Setting the layouts for the panels
		slidePane.setLayout(card);
		textPane.setLayout(cardText);

		//logic to add each of the slides and text
		slides.forEach(s -> {
			final JLabel imageLabel = new JLabel(s.getImage());
			imageLabel.addMouseListener(new MouseAdapter() {
				@Override
			    public void mouseClicked(MouseEvent evt) {
					try {
						Desktop.getDesktop().browse(URI.create(s.getUrl()));
					} catch(IOException e) {
						e.printStackTrace();
					}
				}

			});
		imageLabel.setOpaque(true);
		imageLabel.setBackground(Color.BLACK);
			slidePane.add(imageLabel);
			textPane.add(new JLabel(s.getDescription()));
		});

		getContentPane().add(slidePane, BorderLayout.CENTER);
		southPane.add(textPane);

		// Sets the background to black by making all panes take blackbackground argument
		getContentPane().setBackground(blackBackgroundColor);
		slidePane.setBackground(blackBackgroundColor);
		textPane.setBackground(blackBackgroundColor);
		buttonPane.setBackground(blackBackgroundColor);
		southPane.setBackground(blackBackgroundColor);


		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

		btnPrev.setText("Previous");
		btnPrev.addActionListener((ActionEvent e) -> goPrevious());
		buttonPane.add(btnPrev);

		btnNext.setText("Next");
		btnNext.addActionListener((ActionEvent e) -> goNext());

		buttonPane.add(btnNext);

		southPane.add(buttonPane);


		getContentPane().add(southPane, BorderLayout.SOUTH);
	}

	/**
	 * Previous Button Functionality
	 */
	private void goPrevious() {
		card.previous(slidePane);
		cardText.previous(textPane);
	}

	/**
	 * Next Button Functionality
	 */
	private void goNext() {
		card.next(slidePane);
		cardText.next(textPane);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> (new SlideShow()).setVisible(true));
	}

	class Slide {
		private final String description;
		private final String image;
		private final String url;

		public Slide(final String description, final String image, final String url) {
			super();
			this.description = "<html><body>" + description + "</body></html>";
            // Ensure getResource path is correct and handles potential null
            java.net.URL imageUrl = getClass().getResource(image);
            if (imageUrl != null) {
                this.image = "<html><body><img width='800' height='500' src='" + imageUrl + "'></body></html>";
            } else {
                // Provide fallback html if image not found, prevents null in string concatenation
                System.err.println("Warning: Image resource not found: " + image);
                this.image = "<html><body>Image not found: " + image + "</body></html>";
            }
			this.url = url;
		}

		public String getDescription() {
			return description;
		}

		public String getImage() {
			return image;
		}

		public String getUrl() {
			return url;
		}

	}
}