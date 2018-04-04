package com.unisinos.patienthelper.Adapters;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.transition.AutoTransition;
import android.support.transition.TransitionManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.unisinos.patienthelper.Class.Util;
import com.unisinos.patienthelper.Database.Alarm;
import com.unisinos.patienthelper.Database.Database;
import com.unisinos.patienthelper.Dialog.Dialog;
import com.unisinos.patienthelper.Dialog.DialogDate;
import com.unisinos.patienthelper.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jever on 26/03/2017.
 */

public class RecyclerAdapterAlarm extends RecyclerView.Adapter<RecyclerAdapterAlarm.ViewHolder> {

    private List<Alarm> list;
    private Activity activity;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public long mCodPatient;
        public long mCodAlarm;
        public TextView mTextViewTime;
        public TextView mTextViewDescription;
        public TextInputEditText mTextInputEditTextDescription;
        public TextInputLayout mTextInputLayoutDescription;
        public Switch mSwitchActive;
        public ConstraintLayout mConstraintLayoutDaysOfTheWeek;
        public ToggleButton mToggleButtonSunday;
        public ToggleButton mToggleButtonMonday;
        public ToggleButton mToggleButtonTuesday;
        public ToggleButton mToggleButtonWednesday;
        public ToggleButton mToggleButtonThursday;
        public ToggleButton mToggleButtonFriday;
        public ToggleButton mToggleButtonSaturday;
        public CheckBox mCheckBoxFinishAt;
        public EditText mEditTextFinishDate;
        public ImageButton mImageButtonDelete;
        public TextView mTextViewDelete;
        public ImageButton mImageButtonShowHide;
        public ProgressBar mProgressBar;


        public ViewGroup mRoot;

        public ViewHolder(View v) {
            super(v);
            mRoot = v.findViewById(R.id.cardViewAlarm);
            mTextViewTime = v.findViewById(R.id.textViewTime);
            mTextViewDescription = v.findViewById(R.id.textViewDescription);
            mTextInputEditTextDescription = v.findViewById(R.id.textInputEditTextDescription);
            mTextInputLayoutDescription = v.findViewById(R.id.textInputLayoutDescription);
            mSwitchActive = v.findViewById(R.id.switchActive);
            mConstraintLayoutDaysOfTheWeek = v.findViewById(R.id.constraintLayoutDaysOfTheWeek);
            mToggleButtonSunday = v.findViewById(R.id.toggleButtonSunday);
            mToggleButtonMonday = v.findViewById(R.id.toggleButtonMonday);
            mToggleButtonTuesday = v.findViewById(R.id.toggleButtonTuesday);
            mToggleButtonWednesday = v.findViewById(R.id.toggleButtonWednesday);
            mToggleButtonThursday = v.findViewById(R.id.toggleButtonThursday);
            mToggleButtonFriday = v.findViewById(R.id.toggleButtonFriday);
            mToggleButtonSaturday = v.findViewById(R.id.toggleButtonSaturday);
            mCheckBoxFinishAt = v.findViewById(R.id.checkBoxFinishAt);
            mEditTextFinishDate = v.findViewById(R.id.editTextFinishDate);
            mImageButtonDelete = v.findViewById(R.id.imageButtonDelete);
            mTextViewDelete = v.findViewById(R.id.textViewDelete);
            mImageButtonShowHide = v.findViewById(R.id.imageButtonShowHide);
            mProgressBar = v.findViewById(R.id.progressBar);

        }
    }


    public RecyclerAdapterAlarm(Activity activity, List<Alarm> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public RecyclerAdapterAlarm.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_view_alarm, parent, false);

        return new RecyclerAdapterAlarm.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapterAlarm.ViewHolder holder, int position) {
        final Alarm alarm = list.get(position);
        //TransitionManager.beginDelayedTransition(holder.mRoot, new AutoTransition());
        holder.mProgressBar.setVisibility(View.GONE);

        holder.mCodAlarm = alarm.getCodigo();
        holder.mCodPatient = alarm.getCodPaciente();
        holder.mTextViewTime.setText(alarm.getHorario());
        holder.mTextInputEditTextDescription.setText(alarm.getDescricao());
        holder.mTextViewDescription.setText(alarm.getDescricao());
        holder.mSwitchActive.setChecked(alarm.isAtivo());
        holder.mToggleButtonSunday.setChecked(alarm.isDomingo());
        holder.mToggleButtonMonday.setChecked(alarm.isSegunda());
        holder.mToggleButtonTuesday.setChecked(alarm.isTerca());
        holder.mToggleButtonWednesday.setChecked(alarm.isQuarta());
        holder.mToggleButtonThursday.setChecked(alarm.isQuinta());
        holder.mToggleButtonFriday.setChecked(alarm.isSexta());
        holder.mToggleButtonSaturday.setChecked(alarm.isSabado());

        if (alarm.getDataFim() != null) {
            holder.mCheckBoxFinishAt.setChecked(true);
            holder.mEditTextFinishDate.setText(Util.ConverterData(alarm.getDataFim()));
        } else {
            holder.mCheckBoxFinishAt.setChecked(false);
            holder.mEditTextFinishDate.setText("");
        }

        buttonClick(holder);
        changeLayout(holder, false);
    }

    private void callNotifyDataSetChanged() {

        this.notifyDataSetChanged();
    }

    private void buttonClick(final RecyclerAdapterAlarm.ViewHolder holder) {

        holder.mTextViewTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog.showDialogTimePicker(activity, new Dialog.OnDialogTimePicker() {
                    @Override
                    public void onClickOkDialogTimePicker(String timeText) {
                        holder.mTextViewTime.setText(timeText);
                        save(holder);
                    }
                });
            }
        });

        holder.mSwitchActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                save(holder);
            }
        });

        holder.mTextInputEditTextDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog.showDialogImputText(activity, holder.mTextInputEditTextDescription.getText().toString() ,activity.getString(R.string.description_text), new Dialog.OnDialogImputText() {
                    @Override
                    public void onClickOkDialogImputText(String imputText) {
                        holder.mTextInputEditTextDescription.setText(imputText);
                        save(holder);
                    }
                });
            }
        });

        holder.mToggleButtonSunday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                save(holder);
            }
        });

        holder.mToggleButtonMonday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                save(holder);
            }
        });

        holder.mToggleButtonTuesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                save(holder);
            }
        });

        holder.mToggleButtonWednesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                save(holder);
            }
        });

        holder.mToggleButtonThursday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                save(holder);
            }
        });

        holder.mToggleButtonFriday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                save(holder);
            }
        });

        holder.mToggleButtonSaturday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                save(holder);
            }
        });

        holder.mCheckBoxFinishAt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    DialogDate.ShowData(activity, new DialogDate.OnSelectedDate() {
                        @Override
                        public void onSelectedDate(Date date, String dateText) {
                            if (date != null) {
                                holder.mEditTextFinishDate.setText(dateText);
                                holder.mEditTextFinishDate.setVisibility(View.VISIBLE);
                                save(holder);
                            } else {
                                holder.mCheckBoxFinishAt.setChecked(false);
                            }

                        }
                    }, Util.ConverterCalendario(holder.mEditTextFinishDate.getText().toString()));

                } else {
                    holder.mEditTextFinishDate.setText("");
                    holder.mEditTextFinishDate.setVisibility(View.GONE);
                    save(holder);
                }
            }
        });

        holder.mEditTextFinishDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogDate.ShowData(activity, new DialogDate.OnSelectedDate() {
                    @Override
                    public void onSelectedDate(Date date, String dateText) {
                        if (date != null) {
                            holder.mEditTextFinishDate.setText(dateText);
                            holder.mEditTextFinishDate.setVisibility(View.VISIBLE);
                            save(holder);
                        } else {
                            holder.mCheckBoxFinishAt.setChecked(false);
                        }

                    }
                }, Util.ConverterCalendario(holder.mEditTextFinishDate.getText().toString()));

            }
        });

        holder.mImageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Database mDbHelper = new Database(activity);
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                Alarm alarm = Alarm.ConsultarChave(db, holder.mCodAlarm);
                Alarm.ExcluirSQL(db, alarm);
                for (Alarm item : list) {
                    if (item.getCodigo() == alarm.getCodigo()) {
                        list.remove(item);
                        break;
                    }
                }
                callNotifyDataSetChanged();


            }
        });


        holder.mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.mTextViewDelete.getVisibility() == View.GONE)
                    changeLayout(holder, true);
                else
                    changeLayout(holder, false);
            }
        });

        holder.mImageButtonShowHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.mTextViewDelete.getVisibility() == View.GONE)
                    changeLayout(holder, true);
                else
                    changeLayout(holder, false);
            }
        });


    }

    private void save(final RecyclerAdapterAlarm.ViewHolder holder) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Database mDbHelper = new Database(activity);
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                Alarm alarm = Alarm.ConsultarChave(db, holder.mCodAlarm);
                alarm.setHorario(holder.mTextViewTime.getText().toString());
                alarm.setDescricao(holder.mTextInputEditTextDescription.getText().toString());
                alarm.setAtivo(holder.mSwitchActive.isChecked());
                alarm.setDomingo(holder.mToggleButtonSunday.isChecked());
                alarm.setSegunda(holder.mToggleButtonMonday.isChecked());
                alarm.setTerca(holder.mToggleButtonTuesday.isChecked());
                alarm.setQuarta(holder.mToggleButtonWednesday.isChecked());
                alarm.setQuinta(holder.mToggleButtonThursday.isChecked());
                alarm.setSexta(holder.mToggleButtonFriday.isChecked());
                alarm.setSabado(holder.mToggleButtonSaturday.isChecked());
                alarm.setDataFim(Util.ConverterStringDate(holder.mEditTextFinishDate.getText().toString() + " 00:00:00"));
                Alarm.AlterarSQL(db, alarm);
            }
        }).start();

    }

    private void changeLayout(RecyclerAdapterAlarm.ViewHolder holder, boolean expanded) {
        TransitionManager.beginDelayedTransition(holder.mRoot, new AutoTransition());
        if (expanded) {
            holder.mTextViewTime.setVisibility(View.VISIBLE);
            holder.mSwitchActive.setVisibility(View.VISIBLE);
            holder.mTextViewDescription.setVisibility(View.GONE);
            holder.mTextInputLayoutDescription.setVisibility(View.VISIBLE);
            holder.mConstraintLayoutDaysOfTheWeek.setVisibility(View.VISIBLE);
            holder.mCheckBoxFinishAt.setVisibility(View.VISIBLE);
            if (holder.mCheckBoxFinishAt.isChecked()) {
                holder.mEditTextFinishDate.setVisibility(View.VISIBLE);
            } else {
                holder.mEditTextFinishDate.setVisibility(View.GONE);
            }
            holder.mImageButtonDelete.setVisibility(View.VISIBLE);
            holder.mTextViewDelete.setVisibility(View.VISIBLE);
            holder.mImageButtonShowHide.animate().rotation(180).start();
            holder.mProgressBar.setVisibility(View.GONE);
        } else {
            holder.mTextViewTime.setVisibility(View.VISIBLE);
            holder.mSwitchActive.setVisibility(View.VISIBLE);
            holder.mTextViewDescription.setVisibility(View.VISIBLE);
            holder.mTextViewDescription.setText(holder.mTextInputEditTextDescription.getText().toString());
            holder.mTextInputLayoutDescription.setVisibility(View.GONE);
            holder.mConstraintLayoutDaysOfTheWeek.setVisibility(View.GONE);
            holder.mCheckBoxFinishAt.setVisibility(View.GONE);
            holder.mEditTextFinishDate.setVisibility(View.GONE);
            holder.mImageButtonDelete.setVisibility(View.GONE);
            holder.mTextViewDelete.setVisibility(View.GONE);
            holder.mImageButtonShowHide.animate().rotation(0).start();
            holder.mProgressBar.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}