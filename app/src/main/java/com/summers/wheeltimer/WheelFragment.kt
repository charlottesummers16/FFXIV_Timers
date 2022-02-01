package com.summers.wheeltimer

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.DateFormatSymbols
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

class WheelFragment : Fragment() {

    companion object {
        const val TAG = "WHEEL_FRAGMENT"
        private const val FILE_KEY = "WHEEL_TIMER_FILE_KEY"
        fun newInstance() : WheelFragment{
            return WheelFragment()
        }
    }

    private lateinit var sharedPref: SharedPreferences
    private var day = -1
    private var month = -1
    private var year = -1
    private var hour = -1
    private var minute = -1
    private var type = ""
    private lateinit var fragmentView: View
    private lateinit var fragmentContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        initSharedPreferences()
//        loadSavedData()
//        setSpinners()
//        initView(!hasReturnTimePassed())

//        fragmentView.findViewById<Button>(R.id.btSave)?.setOnClickListener {
//            saveNewData()
//            showHideSpinners(false)
//        }
//        fragmentView.findViewById<Button>(R.id.btClearData)?.setOnClickListener { clearPastDataDialog() }
        Toast.makeText(context, "Wheel loading", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        fragmentContext = container!!.context
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_wheel, container, false)
        return fragmentView
    }

    //Initialise spinners
    private fun setSpinners() {
        val finish = Calendar.getInstance()
        var finishHour = LocalDateTime.now().hour + 22
        var finishDate = finish.get(Calendar.DAY_OF_MONTH) +2
        if (finishHour >= 24) {
            finishDate++
            finishHour = LocalDateTime.now().hour -2
        }
        fragmentView.findViewById<DatePicker>(R.id.spDatePicker).init(
                finish.get(Calendar.YEAR),
                finish.get(Calendar.MONTH),
                finishDate, null
        )
        fragmentView.findViewById<TimePicker>(R.id.spTimePicker).apply {
            setIs24HourView(true)
            hour = finishHour
            minute = LocalDateTime.now().minute
        }
    }

    //Initialise SharedPreference
    private fun initSharedPreferences() {
        sharedPref = fragmentContext.getSharedPreferences(FILE_KEY, Context.MODE_PRIVATE)
    }

    //Load from SharedPreferences
    private fun loadSavedData() {
        type = sharedPref.getString("TYPE", "") ?: ""
        day = sharedPref.getInt("DAY", -1)
        month = sharedPref.getInt("MONTH", -1)
        year = sharedPref.getInt("YEAR", -1)
        hour = sharedPref.getInt("HOUR", -1)
        minute = sharedPref.getInt("MINUTE", -1)

        //Set the TextViews
        if (type.isNotBlank() || day > 0 || month > 0 || hour > 0 || minute > 0 || year > 0) {
            initView(true)
            showHideSpinners(false)
        } else {
            initView(false)
        }
    }

    private fun initView(data: Boolean) {
        if (data && (day > 0)) {
            fragmentView.findViewById<TextView>(R.id.tvNoData).visibility = View.GONE
            fragmentView.findViewById<TextView>(R.id.tvTypeLabel).visibility = View.VISIBLE
            fragmentView.findViewById<TextView>(R.id.tvFinishDateLabel).visibility = View.VISIBLE
            fragmentView.findViewById<TextView>(R.id.tvRemainingTimeLabel).visibility = View.VISIBLE
            fragmentView.findViewById<TextView>(R.id.tvType).visibility = View.VISIBLE
            fragmentView.findViewById<TextView>(R.id.tvFinishDate).visibility = View.VISIBLE
            fragmentView.findViewById<TextView>(R.id.tvRemainingTime).visibility = View.VISIBLE
            fragmentView.findViewById<Button>(R.id.btSave).visibility = View.GONE
            fragmentView.findViewById<Button>(R.id.btClearData).visibility = View.VISIBLE
            populateTextViews()
        } else {
            fragmentView.findViewById<TextView>(R.id.tvNoData).visibility = View.VISIBLE
            fragmentView.findViewById<TextView>(R.id.tvTypeLabel).visibility = View.GONE
            fragmentView.findViewById<TextView>(R.id.tvFinishDateLabel).visibility = View.GONE
            fragmentView.findViewById<TextView>(R.id.tvRemainingTimeLabel).visibility = View.GONE
            fragmentView.findViewById<TextView>(R.id.tvType).visibility = View.GONE
            fragmentView.findViewById<TextView>(R.id.tvFinishDate).visibility = View.GONE
            fragmentView.findViewById<TextView>(R.id.tvRemainingTime).visibility = View.GONE
            fragmentView.findViewById<Button>(R.id.btSave).visibility = View.VISIBLE
            fragmentView.findViewById<Button>(R.id.btClearData).visibility = View.GONE
        }
    }

    //Populate TextViews
    @SuppressLint("SetTextI18n")
    private fun populateTextViews() {
        fragmentView.findViewById<TextView>(R.id.tvType).text = type
        val decimalFormat = DecimalFormat("00")
        val hourString = decimalFormat.format(hour)
        val minuteString = decimalFormat.format(minute)
        fragmentView.findViewById<TextView>(R.id.tvFinishDate).text = "$day ${DateFormatSymbols().months[month - 1]} at $hourString:$minuteString"
    }

    //Save user inputs
    private fun saveNewData() {
        sharedPref.edit()
                .putString("TYPE", fragmentView.findViewById<EditText>(R.id.etType).text.toString())
                .putInt("DAY", fragmentView.findViewById<DatePicker>(R.id.spDatePicker).dayOfMonth)
                .putInt("MONTH", fragmentView.findViewById<DatePicker>(R.id.spDatePicker).month)
                .putInt("YEAR", fragmentView.findViewById<DatePicker>(R.id.spDatePicker).year)
                .putInt("HOUR", fragmentView.findViewById<TimePicker>(R.id.spTimePicker).hour)
                .putInt("MINUTE", fragmentView.findViewById<TimePicker>(R.id.spTimePicker).minute)
                .apply()
        updateVariables()
        workOutTimeRemaining()
        initView(true)
    }

    //Update variables
    private fun updateVariables() {
        val datePicker = fragmentView.findViewById<DatePicker>(R.id.spDatePicker)
        val timePicker = fragmentView.findViewById<TimePicker>(R.id.spTimePicker)
        type = fragmentView.findViewById<EditText>(R.id.etType).text.toString()
        day = datePicker.dayOfMonth
        month = datePicker.month
        year = datePicker.year
        hour = timePicker.hour
        minute = timePicker.minute
    }


    //Work out remaining time
    @SuppressLint("SetTextI18n")
    private fun hasReturnTimePassed() : Boolean {
        val finishDate = "$day/${month-1}/$year $hour:$minute"
        val today = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val getCurrentDateTime = sdf.format(today.time)

        if (day > 0) {
            workOutTimeRemaining()
        }

        return getCurrentDateTime > finishDate
    }

    //Work out time remaining
    private fun workOutTimeRemaining() {
        val todayAsLong = System.currentTimeMillis() //Current time as a long

        val decimalFormat = DecimalFormat("00")
        val hourString = decimalFormat.format(hour)
        val minuteString = decimalFormat.format(minute)
        val monthString = decimalFormat.format(month+1)
        val dayString = decimalFormat.format(day)

        val date = "$dayString/$monthString/$year $hourString:$minuteString"
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", Locale.ENGLISH)
        val localDate = LocalDateTime.parse(date, formatter)
        val finishTimeInMilliseconds = localDate.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli()


        var difference = finishTimeInMilliseconds - todayAsLong
        if (difference > 0) {
            val secondsInMilli: Long = 1000
            val minutesInMilli: Long = secondsInMilli * 60
            val hoursInMilli: Long = minutesInMilli * 60
            val daysInMilli: Long = hoursInMilli * 24
            val daysDifference = (difference / daysInMilli).toInt()
            difference %= daysInMilli
            val hoursDifference = (difference / hoursInMilli).toInt()
            difference %= hoursInMilli
            val minutesDifference = (difference / minutesInMilli).toInt()
            difference %= minutesInMilli
            val secondsDifference = (difference / secondsInMilli).toInt()

            val daysTextString = if (daysDifference == 1) {fragmentContext.getString(R.string.day)} else {fragmentContext.getString(R.string.days)}
            val hoursTextString = if (hoursDifference == 1) {fragmentContext.getString(R.string.hour)} else {fragmentContext.getString(R.string.hours)}
            val minutesTextString = if (minutesDifference == 1) {fragmentContext.getString(R.string.minute)} else {fragmentContext.getString(R.string.minutes)}
            val secondsTextString = if (secondsDifference == 1) {fragmentContext.getString(R.string.second)} else {fragmentContext.getString(R.string.seconds)}

            view?.findViewById<TextView>(R.id.tvRemainingTime)?.text = "$daysDifference $daysTextString, $hoursDifference $hoursTextString,\n$minutesDifference $minutesTextString,\n$secondsDifference $secondsTextString"
        }
    }

    //Ask the user to clear the data
    private fun clearPastDataDialog() {
        val builder = MaterialAlertDialogBuilder(fragmentContext)
        builder.setTitle(R.string.alert_title)
        builder.setMessage(R.string.alert_message)
        builder.setPositiveButton(R.string.yes) { dialog, _ ->
            clearPastData()
            dialog.dismiss()
        }
        builder.setNegativeButton(R.string.no) { dialog, _ ->
            dialog.dismiss()
        }
        builder.create()
        builder.show()
    }

    //Clear the TextViews and the SharedPreferences if the finish time has passed
    private fun clearPastData() {
        initView(false)
        sharedPref.edit()
                .putString("TYPE", "")
                .putInt("DAY", -1)
                .putInt("MONTH", -1)
                .putInt("YEAR", -1)
                .putInt("HOUR", -1)
                .putInt("MINUTE", -1)
                .apply()
        showHideSpinners(true)
        setSpinners()
    }

    //Hide the spinners
    private fun showHideSpinners(show: Boolean) { //True = show them, False = hide them
        if (show) {
            fragmentView.findViewById<View>(R.id.vwSplitter).visibility = View.VISIBLE
            fragmentView.findViewById<TextView>(R.id.tvWheelTypeLabel2).visibility = View.VISIBLE
            fragmentView.findViewById<TextView>(R.id.tvFinishDateLabel2).visibility = View.VISIBLE
            fragmentView.findViewById<TextView>(R.id.tvFinishTimeLabel2).visibility = View.VISIBLE
            fragmentView.findViewById<EditText>(R.id.etType).visibility = View.VISIBLE
            fragmentView.findViewById<DatePicker>(R.id.spDatePicker).visibility = View.VISIBLE
            fragmentView.findViewById<TimePicker>(R.id.spTimePicker).visibility = View.VISIBLE
        } else {
            fragmentView.findViewById<View>(R.id.vwSplitter).visibility = View.GONE
            fragmentView.findViewById<TextView>(R.id.tvWheelTypeLabel2).visibility = View.GONE
            fragmentView.findViewById<TextView>(R.id.tvFinishDateLabel2).visibility = View.GONE
            fragmentView.findViewById<TextView>(R.id.tvFinishTimeLabel2).visibility = View.GONE
            fragmentView.findViewById<EditText>(R.id.etType).visibility = View.GONE
            fragmentView.findViewById<DatePicker>(R.id.spDatePicker).visibility = View.GONE
            fragmentView.findViewById<TimePicker>(R.id.spTimePicker).visibility = View.GONE
        }
    }

}