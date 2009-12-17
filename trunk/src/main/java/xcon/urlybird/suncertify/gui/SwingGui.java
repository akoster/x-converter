package xcon.urlybird.suncertify.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class SwingGui extends JFrame {

	private static final long serialVersionUID = 5165L;

	private Controller controller;

	private JTable mainTable = new JTable();

	private JLabel commentLabel = new JLabel();

	private JTextField nameSearchField = new JTextField(20);
	private JTextField locationSearchField = new JTextField(20);
	private JLabel nameLabel = new JLabel("name");
	private JLabel locationLabel = new JLabel("location");

	private TableModel tableData;

	// private Logger LOG = Logger.getLogger("sampleproject.gui");

	public SwingGui() {
		super("Urly bird application");
		this.setDefaultCloseOperation(SwingGui.EXIT_ON_CLOSE);

		controller = new Controller();

		// Add the menu bar
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem quitMenuItem = new JMenuItem("Quit");
		quitMenuItem.addActionListener(new QuitApplication());
		quitMenuItem.setMnemonic(KeyEvent.VK_Q);
		fileMenu.add(quitMenuItem);
		fileMenu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(fileMenu);

		this.setJMenuBar(menuBar);

		tableData = controller.getHotelRooms();
		this.mainTable.setModel(tableData);

		this.add(new HotelRoomScreen());

		this.pack();
		this.setSize(650, 300);

		// Center on screen
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((d.getWidth() - this.getWidth()) / 2);
		int y = (int) ((d.getHeight() - this.getHeight()) / 2);
		this.setLocation(x, y);
		this.setVisible(true);
	}

	private class HotelRoomScreen extends JPanel {

		private static final long serialVersionUID = 5165L;

		public HotelRoomScreen() {
			this.setLayout(new BorderLayout());
			JScrollPane tableScroll = new JScrollPane(mainTable);
			tableScroll.setSize(500, 250);

			this.add(tableScroll, BorderLayout.CENTER);

			JButton searchButton = new JButton("Search");
			searchButton.addActionListener(new SearchHotelRoom());
			searchButton.setMnemonic(KeyEvent.VK_S);
			JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
			searchPanel.add(nameLabel);
			searchPanel.add(nameSearchField);
			searchPanel.add(locationLabel);
			searchPanel.add(locationSearchField);
			searchPanel.add(searchButton);

			JButton BookButton = new JButton("book selected room");

			BookButton.addActionListener(new BookHotelRoom());
			BookButton.setRequestFocusEnabled(false);
			BookButton.setMnemonic(KeyEvent.VK_B);
			JPanel bookingPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			bookingPanel.add(BookButton);

			JPanel commentPanel = new JPanel();
			commentPanel.add(commentLabel);

			JPanel bottomPanel = new JPanel(new BorderLayout());
			bottomPanel.add(searchPanel, BorderLayout.NORTH);
			bottomPanel.add(bookingPanel, BorderLayout.EAST);
			bottomPanel.add(commentLabel, BorderLayout.SOUTH);
			this.add(bottomPanel, BorderLayout.SOUTH);

			mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			mainTable.setToolTipText("Select a hotel record book a room.");

			BookButton
					.setToolTipText("book a room selected in the above table.");
			nameSearchField
					.setToolTipText("Enter the name of the hotel you want to find.");
			locationSearchField
					.setToolTipText("Enter the name of the hotel you want to find");
			searchButton.setToolTipText("Submit the Hotelroom search.");

		}
	}

	private class BookHotelRoom implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			String id = "";
			int index = mainTable.getSelectedRow();
			if (index >= 0) {
				id = (String) mainTable.getValueAt(index, 0);
				System.out.println("id:" + id);
				commentLabel.setText("book");
				controller.bookRoom(Long.valueOf(id));
			}
		}
	}

	private class SearchHotelRoom implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			String searchHotelNameString = nameSearchField.getText();
			String searchHotelLocationString = locationSearchField.getText();
			System.out.println(" searchHotelNameString: "
					+ searchHotelNameString + " searchHotelLocationString"
					+ searchHotelLocationString);
			commentLabel.setText("search");

		}
	}

	private class QuitApplication implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		new SwingGui();
	}

}
