package com.unisinos.patienthelper.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.unisinos.patienthelper.Adapters.AdapterDialogPatient;
import com.unisinos.patienthelper.Database.Database;
import com.unisinos.patienthelper.Database.Paciente;
import com.unisinos.patienthelper.R;

import java.util.List;

public class DialogSearch extends Dialog {

    private OnListDataListener onListDataListener = null;
    private OnSelectedItemListener onSelectedItemListener = null;
    private Handler handler;
    private Runnable runnable;
    private Activity activity;
    private boolean showBoxSearch;
    private boolean usaTrimSearch = true;
    private int buttonPositiveColor = -1;
    private int buttonNegativeColor = -1;

    public int getButtonPositiveColor() {
        return buttonPositiveColor;
    }

    public void setButtonPositiveColor(int buttonColor) {
        this.buttonPositiveColor = buttonColor;
    }

    public int getButtonNegativeColor() {
        return buttonNegativeColor;
    }

    public void setButtonNegativeColor(int buttonColor) {
        this.buttonNegativeColor = buttonColor;
    }

    public boolean isUseTrimSearch() {
        return usaTrimSearch;
    }

    public void setUseTrimSearch(boolean usaTrimSearch) {
        this.usaTrimSearch = usaTrimSearch;
    }

    public boolean isShowBoxSearch() {
        return showBoxSearch;
    }

    public void setShowBoxSearch(boolean showBoxSearch) {
        this.showBoxSearch = showBoxSearch;
    }

    public interface OnListDataListener {
        void onListData(Dialog dialog, String search);
    }

    public interface OnListDataActivityListener {
        void onListData(Activity ctivity, String search);
    }

    public interface OnSelectedItemListener {
        void onSelectedItem(Object item, int index);
    }

    public DialogSearch(Activity activity, OnListDataListener listData, OnSelectedItemListener selectedItem) {
        super(activity);
        this.activity = activity;
        onListDataListener = listData;
        onSelectedItemListener = selectedItem;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog_search);
        final ListView lvwSearch = (ListView) findViewById(R.id.lvwSearch);
        final Button btnOk = (Button) findViewById(R.id.btnOk);
        final Button btnCancelar = (Button) findViewById(R.id.btnCancel);

        lvwSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onSelectedItemListener != null)
                    onSelectedItemListener.onSelectedItem(lvwSearch.getAdapter().getItem(position), position);
                DialogSearch.this.dismiss();
            }
        });
        if (buttonPositiveColor != -1) {
            btnOk.setTextColor(buttonPositiveColor);
            btnOk.setTextColor(buttonPositiveColor);
        }
        if (buttonNegativeColor != -1) {
            btnCancelar.setTextColor(buttonNegativeColor);
            btnCancelar.setTextColor(buttonNegativeColor);
        }
        EditText txtSearch = (EditText) findViewById(R.id.txtSearch);
        if (!isShowBoxSearch())
            txtSearch.setVisibility(View.GONE);

        txtSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    SelecionarPrimeiroItem(lvwSearch);
                    return false;
                }
                return false;
            }
        });

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (handler != null)
                    handler.removeCallbacks(runnable);
                lvwSearch.setAdapter(null);
                btnOk.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(final Editable s) {
                handler = new Handler();
                runnable = new Runnable() {
                    public void run() {
                        if (DialogSearch.this.isShowing()) {
                            if (onListDataListener != null)
                                onListDataListener.onListData(DialogSearch.this, usaTrimSearch ? s.toString().trim() : s.toString());
                            btnOk.setEnabled(true);
                        }
                    }
                };
                handler.postDelayed(runnable, 1500);
            }
        });


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelecionarPrimeiroItem(lvwSearch);
            }
        });


        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogSearch.this.dismiss();
            }
        });
        if (onListDataListener != null)
            onListDataListener.onListData(this, "");
    }

    private void SelecionarPrimeiroItem(ListView lvw) {
        Object objeto = ObterPrimeiroItem(lvw);
        if (objeto == null) {
            Toast.makeText(activity, R.string.nothing_to_show, Toast.LENGTH_SHORT);
            return;
        }
        if (onSelectedItemListener != null)
            onSelectedItemListener.onSelectedItem(objeto, 0);
        DialogSearch.this.dismiss();
    }

    private Object ObterPrimeiroItem(ListView lvw) {
        ListAdapter adapter = lvw.getAdapter();
        if (adapter == null) {
            return null;
        }
        if (adapter.getCount() == 0) {
            return null;
        }
        return adapter.getItem(0);
    }


    public static void ShowPatients(final Activity context, DialogSearch.OnSelectedItemListener selectedItem) {
        DialogSearch search = new DialogSearch(context,
                new DialogSearch.OnListDataListener() {
                    @Override
                    public void onListData(final Dialog dialog, String search) {
                        search = search.replace(" ", "%");
                        search = search + "%";
                        final ListView lvwSearch = (ListView) dialog.findViewById(R.id.lvwSearch);
                        final String finalSearch = search;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    DialogApp.ShowProgress(context, context.getString(R.string.loading_patients));
                                    Database mDbHelper = new Database(context);
                                    SQLiteDatabase db = mDbHelper.getWritableDatabase();
                                    List<Paciente> patient = null;
                                    patient = Paciente.ConsultarSQL(db, "");
                                    mDbHelper.close();
                                    db.close();
                                    if (patient == null)
                                        return;
                                    final AdapterDialogPatient adapter = new AdapterDialogPatient(context, R.layout.layout_item_list_text, patient);
                                    context.runOnUiThread(new Runnable() {
                                        public void run() {
                                            lvwSearch.setAdapter(adapter);
                                        }
                                    });
                                    DialogApp.CloseProgress();
                                } catch (Exception e) {
                                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT);
                                    DialogApp.CloseProgress();
                                }
                            }
                        }).start();
                    }
                }
                , selectedItem);
        search.setShowBoxSearch(true);
        search.setUseTrimSearch(true);
        search.setTitle(R.string.select_patient);
        search.show();
    }

}
