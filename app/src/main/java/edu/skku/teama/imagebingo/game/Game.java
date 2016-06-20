package edu.skku.teama.imagebingo.game;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import edu.skku.teama.imagebingo.R;

public class Game extends AppCompatActivity {
    private int imageID;
    public static int TIME_OUT = 10*1000;
    private int keep;
    private boolean attack;
    private boolean keepgoing;
    private boolean end;
    private HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
    private HashMap<Integer, Integer> map1 = new HashMap<Integer, Integer>();
    private TextView state;
    private ImageView selectedImage;
    private TextView stateDetailed;
    private Button check;
    private ArrayList<ImageButton> bingo;
    private int numOfBingo;
    private ArrayList<Integer> enableButtons;
    private ArrayList<Integer> checkBingo00;
    private ArrayList<Integer> checkBingo01;
    private ArrayList<Integer> checkBingo02;
    private ArrayList<Integer> checkBingo03;
    private ArrayList<Integer> checkBingo04;
    private ArrayList<Integer> checkBingo05;
    private ArrayList<Integer> checkBingo06;
    private ArrayList<Integer> checkBingo07;
    private ArrayList<Integer> checkBingo08;
    private ArrayList<Integer> checkBingo09;
    private static final int[] BINGO_IDS = {
            R.id.bingo00,
            R.id.bingo01,
            R.id.bingo02,
            R.id.bingo03,
            R.id.bingo04,
            R.id.bingo05,
            R.id.bingo06,
            R.id.bingo07,
            R.id.bingo08,
            R.id.bingo09,
            R.id.bingo10,
            R.id.bingo11,
            R.id.bingo12,
            R.id.bingo13,
            R.id.bingo14,
            R.id.bingo15,
    };
    private static final int[] IMAGE_IDS = {
            R.drawable.image_00,
            R.drawable.image_01,
            R.drawable.image_02,
            R.drawable.image_03,
            R.drawable.image_04,
            R.drawable.image_05,
            R.drawable.image_06,
            R.drawable.image_07,
            R.drawable.image_08,
            R.drawable.image_09,
            R.drawable.image_10,
            R.drawable.image_11,
            R.drawable.image_12,
            R.drawable.image_13,
            R.drawable.image_14,
            R.drawable.image_15,
    };

    static final int ACTION_ENABLE_BT = 101;
    BluetoothAdapter mBA;
    ArrayList<String> mArDevice; // 원격 디바이스 목록
    static final String  BLUE_NAME = "BluetoothEx";  // 접속시 사용하는 이름
    // 접속시 사용하는 고유 ID
    static final UUID BLUE_UUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
    ClientThread mCThread = null; // 클라이언트 소켓 접속 스레드
    ServerThread mSThread = null; // 서버 소켓 접속 스레드
    SocketThread mSocketThread = null; // 데이터 송수신 스레드
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#c4c4cf"));
        keep = 0;
        keepgoing = true;
        end = false;
        mArDevice = new ArrayList<String>();
        // 블루투스 사용 가능상태 판단
        boolean isBlue = canUseBluetooth();
        if(isBlue) {
            getParedDevice();
        }
        SetPlayerNum();

        state = (TextView) findViewById(R.id.state);
        selectedImage = (ImageView) findViewById(R.id.selectedImage);
        stateDetailed = (TextView) findViewById(R.id.stateDetailed);
        check = (Button) findViewById(R.id.check);
        bingo = new ArrayList<ImageButton>();
        numOfBingo = 0;
        checkBingo00 = new ArrayList<Integer>();
        checkBingo01 = new ArrayList<Integer>();
        checkBingo02 = new ArrayList<Integer>();
        checkBingo03 = new ArrayList<Integer>();
        checkBingo04 = new ArrayList<Integer>();
        checkBingo05 = new ArrayList<Integer>();
        checkBingo06 = new ArrayList<Integer>();
        checkBingo07 = new ArrayList<Integer>();
        checkBingo08 = new ArrayList<Integer>();
        checkBingo09 = new ArrayList<Integer>();
        enableButtons = new ArrayList<Integer>();
        ArrayList<Integer> ran = new ArrayList<Integer>();
        for(int i = 0; i < 16; i++) {
            ran.add(i);
        }
        final Random bimag = new Random();
        Integer b[] = new Integer[16];
        int img[] = new int[16];
        for(int i = 0; i < 16; i++) {
            int j = bimag.nextInt(ran.size());
            b[i] = ran.get(j);
            ran.remove(j);
        }
        for(int i = 0; i < 16; i++) {
            ImageButton button = (ImageButton)findViewById(BINGO_IDS[i]);
            button.setImageResource(IMAGE_IDS[b[i]]);
            map.put(BINGO_IDS[i], b[i]);
            map1.put(b[i], i);
            bingo.add(button);
        }

        check.setEnabled(false);
        check.setBackgroundColor(Color.parseColor("#c4c4cf"));
        selectedImage.setImageResource(R.drawable.selected);
        for(int i = 0; i < 16; i++) {
            enableButtons.add(i);
            ButtonMethod(i);
        }
    }
    //백버튼 종료
    @Override
    public void onBackPressed() {
        if(!end) {
            android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(this);
            alert.setMessage("게임을 정말 종료하시겠습니까?.").setCancelable(false).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    return;
                }
            }).setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    mSocketThread.write("exit");
                    finish();
                }
            }).show();
        }else {
            Toast.makeText(getApplicationContext(), "확인 버튼을 클릭하세요", Toast.LENGTH_SHORT).show();
        }
    }

    public void CheckBingo(int i) {
        switch (i/4) {
            case 0:
                checkBingo00.add(i);
                if(checkBingo00.size() == 4) numOfBingo++;
                break;
            case 1:
                checkBingo01.add(i);
                if(checkBingo01.size() == 4) numOfBingo++;
                break;
            case 2:
                checkBingo02.add(i);
                if(checkBingo02.size() == 4) numOfBingo++;
                break;
            case 3:
                checkBingo03.add(i);
                if(checkBingo03.size() == 4) numOfBingo++;
                break;
        }
        switch (i%4) {
            case 0:
                checkBingo04.add(i);
                if(checkBingo04.size() == 4) numOfBingo++;
                break;
            case 1:
                checkBingo05.add(i);
                if(checkBingo05.size() == 4) numOfBingo++;
                break;
            case 2:
                checkBingo06.add(i);
                if(checkBingo06.size() == 4) numOfBingo++;
                break;
            case 3:
                checkBingo07.add(i);
                if(checkBingo07.size() == 4) numOfBingo++;
                break;
        }
        if(i/4 == i%4) {
            checkBingo08.add(i);
            if(checkBingo08.size() == 4) numOfBingo++;
        }
        if((i/4 + i%4) == 3) {
            checkBingo09.add(i);
            if(checkBingo09.size() == 4) numOfBingo++;
        }
    }
    public void CheckMethod(final int i) {
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(keep == 0) {
                    check.setEnabled(false);
                    check.setBackgroundColor(Color.parseColor("#c4c4cf"));
                    selectedImage.setImageResource(R.drawable.selected);
                    CheckBingo(i);
                    Integer ch = new Integer(i);
                    enableButtons.remove(ch);
                    for(int j = 0; j < enableButtons.size(); j++) {
                        bingo.get(enableButtons.get(j)).setEnabled(false);
                    }
                    bingo.get(i).setImageResource(R.drawable.selected);
                    bingo.get(i).setEnabled(false);
                    if(mSocketThread == null) {
                        return;
                    }
                    // 사용자가 입력한 텍스트를 소켓으로 전송한다
                    String strBuf = map.get(BINGO_IDS[i]).toString();
                    strBuf += "-"+numOfBingo;
                    if(strBuf.length() < 1) {
                        return;
                    }
                    mSocketThread.write(strBuf);
                    if(numOfBingo < 3) {
                        state.setText("상대 턴");
                        stateDetailed.setText("잠시만 기다려주세요");
                    } else {
                        end = true;
                        keep = 1;
                        state.setText("승리");
                        stateDetailed.setText("축하합니다 !");
                        check.setEnabled(true);
                        for(int j = 0; j < 16; j++) {
                            bingo.get(j).setEnabled(false);
                        }
                        check.setBackgroundColor(Color.parseColor("#96CDCD"));
                        Toast.makeText(getApplicationContext(), "승리하였습니다", Toast.LENGTH_LONG).show();
                        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#60c891"));
                    }
                } else {
                    finish();
                }
            }
        });
    }
    public void ButtonMethod(final int i) {
        bingo.get(i).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check.setEnabled(true);
                check.setBackgroundColor(Color.parseColor("#96CDCD"));
                imageID = IMAGE_IDS[map.get(BINGO_IDS[i])];
                selectedImage.setImageResource(imageID);
                stateDetailed.setText("선택한 그림이 맞으면 확인 버튼을 누르세요");
                CheckMethod(i);
            }
        });
    }
//----------------------------------------------------------------------------------------------------------------------
    //플레이어 순서 선택 다이얼로그
    private void SetPlayerNum() {
        android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(this);
        alert.setMessage("선공/후공을 결정해 주세요.").setNegativeButton("선공", new DialogInterface.OnClickListener() {
            @Override
            public  void onClick(DialogInterface dialog, int id) {
                SetConnect();
            }
        }).setPositiveButton("후공", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                WaitConnect();
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public  void onCancel(DialogInterface dialog) {
                Toast.makeText(getApplicationContext(), "게임이 취소되었습니다", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).show();
    }
    //공격 플레이어 연결 다이얼로그
    private void SetConnect() {
        android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(this);
        alert.setTitle("연결할 기기를 선택해 주세요");
        final ArrayAdapter<String> deviceName = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, mArDevice);
        alert.setAdapter(deviceName,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        progressDialog = ProgressDialog.show(
                                Game.this,
                                "연결중",
                                "잠시만 기다려 주세요",
                                true,
                                true,
                                new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        Toast.makeText(getApplicationContext(), "게임이 취소되었습니다", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                        dHandler.sendEmptyMessageDelayed(TIME_OUT, 10*1000);
                        // 서버 소켓 접속을 위한 스레드 생성 & 시작
                        mSThread = new ServerThread();
                        mSThread.start();
                        // 사용자가 선택한 항목의 내용을 구한다
                        String strItem = mArDevice.get(id);
                        attack = true;
                        for(int i = 0; i < 16; i++) {
                            bingo.get(i).setEnabled(attack);
                        }
                        state.setText("내 턴");
                        stateDetailed.setText("그림을 선택해주세요");
                        // 사용자가 선택한 디바이스의 주소를 구한다
                        int pos = strItem.indexOf(" - ");
                        if( pos <= 0 ) return;
                        String address = strItem.substring(pos + 3);
                        // 디바이스 검색 중지
                        stopFindDevice();
                        // 서버 소켓 스레드 중지
                        mSThread.cancel();
                        mSThread = null;
                        if(mCThread != null) {
                            return;
                        }
                        // 상대방 디바이스를 구한다
                        BluetoothDevice device = mBA.getRemoteDevice(address);
                        // 클라이언트 소켓 스레드 생성 & 시작
                        mCThread = new ClientThread(device);
                        mCThread.start();
                    }
                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getApplicationContext(), "게임이 취소되었습니다", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(getApplicationContext(), "게임이 취소되었습니다", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        alert.show();
    }
    //수비 플레이어 대기 다이얼로그
    private void WaitConnect() {
        // 서버 소켓 접속을 위한 스레드 생성 & 시작
        mSThread = new ServerThread();
        mSThread.start();
        attack = false;
        for(int i = 0; i < 16; i++) {
            bingo.get(i).setEnabled(attack);
        }
        state.setText("상대 턴");
        stateDetailed.setText("잠시만 기다려주세요");
        progressDialog = ProgressDialog.show(
                this,
                "연결중",
                "잠시만 기다려 주세요",
                true,
                true,
                new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Toast.makeText(getApplicationContext(), "게임이 취소되었습니다", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        dHandler.sendEmptyMessageDelayed(TIME_OUT, 10*1000);
    }
    //--------------------------------------------------------------------------------------------------
    // 블루투스 사용 가능상태 판단
    public boolean canUseBluetooth() {
        // 블루투스 어댑터를 구한다
        mBA = BluetoothAdapter.getDefaultAdapter();
        // 블루투스 어댑터가 null 이면 블루투스 장비가 존재하지 않는다.
        if(mBA == null) {
            return false;
        }
        // 블루투스 활성화 상태라면 함수 탈출
        if(mBA.isEnabled()) {
            return true;
        }
        // 사용자에게 블루투스 활성화를 요청한다
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(intent, ACTION_ENABLE_BT);
        return false;
    }
    // 원격 디바이스 검색 시작
    public void startFindDevice() {
        // 원격 디바이스 검색 중지
        stopFindDevice();
        // 디바이스 검색 시작
        mBA.startDiscovery();
        // 원격 디바이스 검색 이벤트 리시버 등록
        registerReceiver(mBlueRecv, new IntentFilter(BluetoothDevice.ACTION_FOUND));
    }
    // 디바이스 검색 중지
    public void stopFindDevice() {
        // 현재 디바이스 검색 중이라면 취소한다
        if(mBA.isDiscovering()) {
            mBA.cancelDiscovery();
            // 브로드캐스트 리시버를 등록 해제한다
            unregisterReceiver(mBlueRecv);
        }
    }
    // 원격 디바이스 검색 이벤트 수신
    BroadcastReceiver mBlueRecv = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction() == BluetoothDevice.ACTION_FOUND) {
                // 인텐트에서 디바이스 정보 추출
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // 페어링된 디바이스가 아니라면
                if(device.getBondState() != BluetoothDevice.BOND_BONDED)
                    // 디바이스를 목록에 추가
                    addDeviceToList(device.getName(), device.getAddress());
            }
        }
    };
    // 디바이스를 ArrayList 에 추가
    public void addDeviceToList(String name, String address) {
        // ArrayList 에 새로운 항목을 추가
        String deviceInfo = name + " - " + address;
        Log.d("tag1", "Device Find: " + deviceInfo);
        mArDevice.add(deviceInfo);
    }
    // 다른 디바이스에게 자신을 검색 허용
    public void setDiscoverable() {
        // 현재 검색 허용 상태라면 함수 탈출
        if(mBA.getScanMode() == BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            return;
        }
        // 다른 디바이스에게 자신을 검색 허용 지정
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
        startActivity(intent);
    }
    // 페어링된 원격 디바이스 목록 구하기
    public void getParedDevice() {
        if(mSThread != null) {
            return;
        }
        // 블루투스 어댑터에서 페어링된 원격 디바이스 목록을 구한다
        Set<BluetoothDevice> devices = mBA.getBondedDevices();
        // 디바이스 목록에서 하나씩 추출
        for(BluetoothDevice device : devices) {
            // 디바이스를 목록에 추가
            addDeviceToList(device.getName(), device.getAddress());
        }
        // 원격 디바이스 검색 시작
        startFindDevice();
        // 다른 디바이스에 자신을 노출
        setDiscoverable();
    }
    // 클라이언트 소켓 생성을 위한 스레드
    private class ClientThread extends Thread {
        private BluetoothSocket mmCSocket;
        // 원격 디바이스와 접속을 위한 클라이언트 소켓 생성
        public ClientThread(BluetoothDevice  device) {
            try {
                mmCSocket = device.createInsecureRfcommSocketToServiceRecord(BLUE_UUID);
            } catch(IOException e) {
                //showMessage("Create Client Socket error");
                return;
            }
        }
        public void run() {
            // 원격 디바이스와 접속 시도
            try {
                mmCSocket.connect();
            } catch(IOException e) {
                //showMessage("Connect to server error");
                // 접속이 실패했으면 소켓을 닫는다
                try {
                    mmCSocket.close();
                } catch (IOException e2) {
                    //showMessage("Client Socket close error");
                }
                return;
            }
            // 원격 디바이스와 접속되었으면 데이터 송수신 스레드를 시작
            onConnected(mmCSocket);
        }
        // 클라이언트 소켓 중지
        public void cancel() {
            try {
                mmCSocket.close();
            } catch (IOException e) {
                //showMessage("Client Socket close error");
            }
        }
    }
    // 서버 소켓을 생성해서 접속이 들어오면 클라이언트 소켓을 생성하는 스레드
    private class ServerThread extends Thread {
        private BluetoothServerSocket mmSSocket;
        // 서버 소켓 생성
        public ServerThread() {
            try {
                mmSSocket = mBA.listenUsingInsecureRfcommWithServiceRecord(BLUE_NAME, BLUE_UUID);
            } catch(IOException e) {
                //showMessage("Get Server Socket Error");
            }
        }
        public void run() {
            BluetoothSocket cSocket = null;
            // 원격 디바이스에서 접속을 요청할 때까지 기다린다
            try {
                cSocket = mmSSocket.accept();
            } catch(IOException e) {
                //showMessage("Socket Accept Error");
                return;
            }
            // 원격 디바이스와 접속되었으면 데이터 송수신 스레드를 시작
            onConnected(cSocket);
        }
        // 서버 소켓 중지
        public void cancel() {
            try {
                mmSSocket.close();
            } catch (IOException e) {
                //showMessage("Server Socket close error");
            }
        }
    }
    // 메시지를 화면에 표시
    public void showMessage(String strMsg) {
        // 메시지 텍스트를 핸들러에 전달
        Message msg = Message.obtain(mHandler, 0, strMsg);
        mHandler.sendMessage(msg);
        Log.d("tag1", strMsg);
    }

    // 메시지 화면 출력을 위한 핸들러
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                String strMsg = (String)msg.obj;
                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
            }
        }
    };
    public void receivedImage(String strMsg) {
        // 메시지 텍스트를 핸들러에 전달
        Message msg = Message.obtain(iHandler, 0, strMsg);
        iHandler.sendMessage(msg);
        Log.d("tag1", strMsg);
    }
    // 이미지 전달을 위한 핸들러
    Handler iHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                String strMsg = (String)msg.obj;
                if(strMsg.equals("exit")) {
                    finish();
                    return;
                }
                String strArr[] = strMsg.split("-");
                int index = Integer.parseInt(strArr[0]);
                if(Integer.parseInt(strArr[1]) >= 3) {
                    end = true;
                    check.setBackgroundColor(Color.parseColor("#96CDCD"));
                    getWindow().getDecorView().setBackgroundColor(Color.parseColor("#d15354"));
                    Toast.makeText(getApplicationContext(), "패배하였습니다", Toast.LENGTH_LONG).show();
                    state.setText("패배");
                    stateDetailed.setText("다음 기회에 ...");
                    keep = 1;
                    check.setEnabled(true);
                    for (int j = 0; j < 16; j++) {
                        bingo.get(j).setEnabled(false);
                    }
                    return;
                }
                state.setText("내 턴");
                stateDetailed.setText("그림을 선택해주세요");
                selectedImage.setImageResource(IMAGE_IDS[index]);
                Integer ch = new Integer(map1.get(index));
                bingo.get(ch).setImageResource(R.drawable.selected);
                bingo.get(ch).setEnabled(false);
                CheckBingo(ch);
                enableButtons.remove(ch);
                if(numOfBingo >= 3) {
                    end = true;
                    keep = 1;
                    check.setEnabled(true);
                    check.setBackgroundColor(Color.parseColor("#96CDCD"));
                    Toast.makeText(getApplicationContext(), "승리하였습니다", Toast.LENGTH_LONG).show();
                    getWindow().getDecorView().setBackgroundColor(Color.parseColor("#60c891"));
                    state.setText("승리");
                    stateDetailed.setText("축하합니다 !");
                    strMsg = strArr[0] + "-" + numOfBingo;
                    mSocketThread.write(strMsg);
                }
                for (int j = 0; j < enableButtons.size(); j++) {
                    bingo.get(enableButtons.get(j)).setEnabled(true);
                }
            }
        }
    };
    Handler dHandler = new Handler() {
        public void handleMessage(Message msg) {
            if(msg.what == TIME_OUT && keepgoing) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "연결에 실패하였습니다", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    };
    // 원격 디바이스와 접속되었으면 데이터 송수신 스레드를 시작
    public void onConnected(BluetoothSocket socket) {
        // 데이터 송수신 스레드가 생성되어 있다면 삭제한다
        if(mSocketThread != null) {
            mSocketThread = null;
        }
        // 데이터 송수신 스레드를 시작
        mSocketThread = new SocketThread(socket);
        mSocketThread.start();
        //프로그레스 다이얼로그 종료
        if(progressDialog != null) {
            progressDialog.dismiss();
            keepgoing = false;
            showMessage("연결에 성공하였습니다");
        }
    }
    // 데이터 송수신 스레드
    private class SocketThread extends Thread {
        private final BluetoothSocket mmSocket; // 클라이언트 소켓
        private InputStream mmInStream; // 입력 스트림
        private OutputStream mmOutStream; // 출력 스트림

        public SocketThread(BluetoothSocket socket) {
            mmSocket = socket;
            // 입력 스트림과 출력 스트림을 구한다
            try {
                mmInStream = socket.getInputStream();
                mmOutStream = socket.getOutputStream();
            } catch (IOException e) {
                showMessage("송신(수신)에 실패하였습니다");
            }
        }
        // 소켓에서 수신된 데이터를 화면에 표시한다
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;

            while (true) {
                try {
                    // 입력 스트림에서 데이터를 읽는다
                    bytes = mmInStream.read(buffer);
                    String strBuf = new String(buffer, 0, bytes);
                    receivedImage(strBuf);
                    SystemClock.sleep(1);
                } catch (IOException e) {
                    if(!end) {
                        showMessage("연결이 끊어졌습니다");
                        finish();
                        break;
                    }
                }
            }
        }
        // 데이터를 소켓으로 전송한다
        public void write(String strBuf) {
            try {
                // 출력 스트림에 데이터를 저장한다
                byte[] buffer = strBuf.getBytes();
                mmOutStream.write(buffer);
            } catch (IOException e) {
                showMessage("전송에 실패하였습니다");
            }
        }
    }
    // 앱이 종료될 때 디바이스 검색 중지
    public void onDestroy() {
        super.onDestroy();
        // 디바이스 검색 중지
        stopFindDevice();
        // 스레드를 종료
        if(mCThread != null) {
            mCThread.cancel();
            mCThread = null;
        }
        if(mSThread != null) {
            mSThread.cancel();
            mSThread = null;
        }
        if(mSocketThread != null) {
            mSocketThread = null;
        }
    }
}