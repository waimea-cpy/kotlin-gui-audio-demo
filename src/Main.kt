/**
 * ===============================================================
 * Kotlin GUI Audio Demo
 * ===============================================================
 *
 * This is a demo showing how audio files can be played in a
 * Kotlin / Swing GUI
 *
 * Audio files are pulled from a sounds folder within the src
 * folder. Audio needs to be **WAV** format (not MP3).
 */

import com.formdev.flatlaf.FlatDarkLaf
import java.awt.*
import java.awt.event.*
import javax.sound.sampled.AudioSystem
import javax.swing.*


/**
 * Launch the application
 */
fun main() {
    FlatDarkLaf.setup()     // Flat, dark look-and-feel
    val app = App()         // Create the app model
    MainWindow(app)         // Create and show the UI, using the app model
}


/**
 * The application class (model)
 * This is the place where any application data should be
 * stored, plus any application logic functions
 */
class App() {
    // Data fields
    val soundFiles = listOf(
        "boom.wav",
        "bell.wav",
        "quack.wav",
        "honk.wav",
        "laser.wav"
    )
    var currentSoundFile = soundFiles.random()

    // Application logic functions
    fun pickRandomSound() {
        currentSoundFile = soundFiles.random()
    }
}


/**
 * Main UI window (view)
 * Defines the UI and responds to events
 * The app model should be passwd as an argument
 */
class MainWindow(val app: App) : JFrame(), ActionListener {

    // Fields to hold the UI elements
    private lateinit var infoLabel: JLabel
    private lateinit var playButton: JButton

    /**
     * Configure the UI and display it
     */
    init {
        configureWindow()               // Configure the window
        addControls()                   // Build the UI

        setLocationRelativeTo(null)     // Centre the window
        isVisible = true                // Make it visible
    }

    /**
     * Configure the main window
     */
    private fun configureWindow() {
        title = "Kotlin Swing GUI Demo"
        contentPane.preferredSize = Dimension(400, 175)
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        isResizable = false
        layout = null

        pack()
    }

    /**
     * Populate the UI with UI controls
     */
    private fun addControls() {
        val baseFont = Font(Font.SANS_SERIF, Font.PLAIN, 24)

        infoLabel = JLabel("Ready to play a sound")
        infoLabel.horizontalAlignment = SwingConstants.CENTER
        infoLabel.bounds = Rectangle(25, 25, 350, 50)
        infoLabel.font = baseFont
        add(infoLabel)

        playButton = JButton("Play a Sound")
        playButton.bounds = Rectangle(25, 100, 350, 50)
        playButton.font = baseFont
        playButton.addActionListener(this)     // Handle any clicks
        add(playButton)
    }


    /**
     * Update the UI controls based on the current state
     * of the application model
     */
    fun updateView() {
        infoLabel.text = "Playing ${app.currentSoundFile}..."
    }

    /**
     * Handle any UI events (e.g. button clicks)
     * Usually this involves updating the application model
     * then refreshing the UI view
     */
    override fun actionPerformed(e: ActionEvent?) {
        when (e?.source) {
            playButton -> {
                // Select a sound to play from app data
                app.pickRandomSound()
                // Update the view's info
                updateView()

                // Play the sound
                val soundFile = this::class.java.getResourceAsStream("sounds/" + app.currentSoundFile)
                val soundStream = AudioSystem.getAudioInputStream(soundFile)
                val soundClip = AudioSystem.getClip()
                soundClip.open(soundStream)
                soundClip.start()
            }
        }
    }

}

