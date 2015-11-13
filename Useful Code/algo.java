public class YourActivityClass extends Activity implements AdapterView.OnItemClickListener {

    private ListView mListView;
    private ArrayList<String> names;
    private ArrayList<Drawable> images;
    private ArrayList<String> addresses;
    private BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBluetoothAdapter = BluetoothAdapter.getBluetoothAdapter();
        mListView = (ListView) findViewById(R.id.paired_devices_listview);
        names = new ArrayList<String>();
        images = new ArrayList<Drawable>();
        addresses = new ArrayList<String>();
        for (BluetoothDevice device : mBluetoothAdapter.getBondedDevices()) {
            names.add(device.getName());
            images.add(getDrawableByMajorClass(device.getBluetoothClass().getMajorDeviceClass()));
            addresses.add(device.getAddress());
        }
        CustomAdapter adapter = new CustomAdapter(this, names, images, addresses);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
    }

    private Drawable getDrawableByMajorClass(int major) {
        Drawable drawable = null;
        switch (major) {
            case BluetoothClass.Device.Major.COMPUTER:
                drawable = getResources().getDrawable(R.drawable.computer_icon);
                break;
            case BluetoothClass.Device.Major.PHONE:
                drawable = getResources().getDrawable(R.drawable.phone_icon);
                break;
            default:
                drawable = getResources().getDrawable(R.drawable.some_bluetooth_device_icon);
                break;
        }
        return drawable;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //String value is the address...
        //...of selected device
        String value = (String) mListView.getItemAtPosition(position);

        //Do your stuff here
    }

}
