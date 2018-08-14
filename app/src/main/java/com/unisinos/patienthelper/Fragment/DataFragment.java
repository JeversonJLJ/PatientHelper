package com.unisinos.patienthelper.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.transition.AutoTransition;
import android.support.transition.TransitionManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.unisinos.patienthelper.Database.Alarm;
import com.unisinos.patienthelper.Database.Database;
import com.unisinos.patienthelper.Database.Paciente;
import com.unisinos.patienthelper.Dialog.DialogApp;
import com.unisinos.patienthelper.Dialog.DialogDate;
import com.unisinos.patienthelper.R;
import com.unisinos.patienthelper.Class.Util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by jever on 25/03/2018.
 */


public class DataFragment extends Fragment {

    private View mRootView;
    private ViewGroup mRootViewGroup;
    public long codPatient;
    private Paciente mPatient;
    private TextInputEditText mEditTextName;
    private EditText mEditTextBirthDate;
    private EditText mEditTextAge;
    private EditText mEditTextComments;
    private String mCurrentColorSelectd;
    private Animation mAnimShow, mAnimHide;
    private CardView mCardViewColor;
    private Button mFabSelectedColor, mFab26a69a, mFab42a5f5, mFab66bb6a, mFab78909c, mFab8d6e63, mFabab47bc, mFabd4e157, mFabec407a, mFabef5350, mFabffee58;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            mRootView = inflater.inflate(R.layout.fragment_patient_data, container, false);
            mRootViewGroup = (ViewGroup) mRootView;
            mEditTextName = mRootView.findViewById(R.id.textInputEditTextName);
            mEditTextBirthDate = mRootView.findViewById(R.id.editTextBirthDate);
            mEditTextAge = mRootView.findViewById(R.id.editTextAge);
            mEditTextComments = mRootView.findViewById(R.id.editTextComments);

            mEditTextBirthDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogDate.ShowData(getActivity(), new DialogDate.OnSelectedDate() {
                        @Override
                        public void onSelectedDate(Date date, String dateText) {
                            if (date != null && !dateText.isEmpty()) {
                                mEditTextBirthDate.setText(dateText);
                                updateAge(date);
                            }


                        }
                    }, Util.ConverterCalendario(mEditTextBirthDate.getText().toString()));
                }
            });

            mCardViewColor = mRootView.findViewById(R.id.cardViewColors);
            mFabSelectedColor = mRootView.findViewById(R.id.fab_selected_color);
            mFab26a69a = mRootView.findViewById(R.id.fab_color_26a69a);
            mFab42a5f5 = mRootView.findViewById(R.id.fab_color_42a5f5);
            mFab66bb6a = mRootView.findViewById(R.id.fab_color_66bb6a);
            mFab78909c = mRootView.findViewById(R.id.fab_color_78909c);
            mFab8d6e63 = mRootView.findViewById(R.id.fab_color_8d6e63);
            mFabab47bc = mRootView.findViewById(R.id.fab_color_ab47bc);
            mFabd4e157 = mRootView.findViewById(R.id.fab_color_d4e157);
            mFabec407a = mRootView.findViewById(R.id.fab_color_ec407a);
            mFabef5350 = mRootView.findViewById(R.id.fab_color_ef5350);
            mFabffee58 = mRootView.findViewById(R.id.fab_color_ffee58);
            initAnimation();
            colorSelect();
            loadData();
        } catch (Exception e) {
            Toast.makeText(this.getActivity(), e.getMessage(), Toast.LENGTH_SHORT);
        }
        return mRootView;
    }

    private void initAnimation() {
        mAnimShow = AnimationUtils.loadAnimation(this.getActivity(), R.anim.anim_view_show_right_to_left);
        mAnimHide = AnimationUtils.loadAnimation(this.getActivity(), R.anim.anim_view_hide_left_to_right);
    }

    public Drawable getColor(String color) {
        try {
            switch (color) {
                case "42a5f5":
                    return ContextCompat.getDrawable(mRootView.getContext(), R.drawable.shape_button_color_42a5f5);
                case "66bb6a":
                    return ContextCompat.getDrawable(mRootView.getContext(), R.drawable.shape_button_color_66bb6a);
                case "78909c":
                    return ContextCompat.getDrawable(mRootView.getContext(), R.drawable.shape_button_color_78909c);
                case "8d6e63":
                    return ContextCompat.getDrawable(mRootView.getContext(), R.drawable.shape_button_color_8d6e63);
                case "ab47bc":
                    return ContextCompat.getDrawable(mRootView.getContext(), R.drawable.shape_button_color_ab47bc);
                case "d4e157":
                    return ContextCompat.getDrawable(mRootView.getContext(), R.drawable.shape_button_color_d4e157);
                case "ec407a":
                    return ContextCompat.getDrawable(mRootView.getContext(), R.drawable.shape_button_color_ec407a);
                case "ef5350":
                    return ContextCompat.getDrawable(mRootView.getContext(), R.drawable.shape_button_color_ef5350);
                case "ffee58":
                    return ContextCompat.getDrawable(mRootView.getContext(), R.drawable.shape_button_color_ffee58);
                default:
                    return ContextCompat.getDrawable(mRootView.getContext(), R.drawable.shape_button_color_26a69a);
            }
        } catch (Exception e) {
            Toast.makeText(this.getActivity(), e.getMessage(), Toast.LENGTH_SHORT);
            return ContextCompat.getDrawable(mRootView.getContext(), R.drawable.shape_button_color_26a69a);
        }

    }

    private void colorSelect() {

        mCardViewColor.startAnimation(mAnimHide);
        mFabSelectedColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCardViewColor.getVisibility() == View.VISIBLE)
                    mCardViewColor.startAnimation(mAnimHide);
                else
                    mCardViewColor.startAnimation(mAnimShow);
            }
        });

        mFab26a69a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFabSelectedColor.setBackground(ContextCompat.getDrawable(mRootView.getContext(), R.drawable.shape_button_color_26a69a));
                mCurrentColorSelectd = "26a69a";
                mCardViewColor.startAnimation(mAnimHide);
            }
        });

        mFab42a5f5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFabSelectedColor.setBackground(ContextCompat.getDrawable(mRootView.getContext(), R.drawable.shape_button_color_42a5f5));
                mCurrentColorSelectd = "42a5f5";
                mCardViewColor.startAnimation(mAnimHide);
            }
        });

        mFab66bb6a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFabSelectedColor.setBackground(ContextCompat.getDrawable(mRootView.getContext(), R.drawable.shape_button_color_66bb6a));
                mCurrentColorSelectd = "66bb6a";
                mCardViewColor.startAnimation(mAnimHide);
            }
        });

        mFab78909c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFabSelectedColor.setBackground(ContextCompat.getDrawable(mRootView.getContext(), R.drawable.shape_button_color_78909c));
                mCurrentColorSelectd = "78909c";
                mCardViewColor.startAnimation(mAnimHide);
            }
        });

        mFab8d6e63.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFabSelectedColor.setBackground(ContextCompat.getDrawable(mRootView.getContext(), R.drawable.shape_button_color_8d6e63));
                mCurrentColorSelectd = "8d6e63";
                mCardViewColor.startAnimation(mAnimHide);
            }
        });

        mFabab47bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFabSelectedColor.setBackground(ContextCompat.getDrawable(mRootView.getContext(), R.drawable.shape_button_color_ab47bc));
                mCurrentColorSelectd = "ab47bc";
                mCardViewColor.startAnimation(mAnimHide);
            }
        });

        mFabd4e157.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFabSelectedColor.setBackground(ContextCompat.getDrawable(mRootView.getContext(), R.drawable.shape_button_color_d4e157));
                mCurrentColorSelectd = "d4e157";
                mCardViewColor.startAnimation(mAnimHide);
            }
        });

        mFabec407a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFabSelectedColor.setBackground(ContextCompat.getDrawable(mRootView.getContext(), R.drawable.shape_button_color_ec407a));
                mCurrentColorSelectd = "ec407a";
                mCardViewColor.startAnimation(mAnimHide);
            }
        });

        mFabef5350.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFabSelectedColor.setBackground(ContextCompat.getDrawable(mRootView.getContext(), R.drawable.shape_button_color_ef5350));
                mCurrentColorSelectd = "ef5350";
                mCardViewColor.startAnimation(mAnimHide);
            }
        });

        mFabffee58.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFabSelectedColor.setBackground(ContextCompat.getDrawable(mRootView.getContext(), R.drawable.shape_button_color_ffee58));
                mCurrentColorSelectd = "ffee58";
                mCardViewColor.startAnimation(mAnimHide);
            }
        });

        Animation.AnimationListener animationListenerHide = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mCardViewColor.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };

        Animation.AnimationListener animationListenerShow = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mCardViewColor.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };

        mAnimHide.setAnimationListener(animationListenerHide);
        mAnimShow.setAnimationListener(animationListenerShow);

    }

    private void updateAge(Date birthDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthDate);
        int age = Calendar.getInstance().get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
        age = (Calendar.getInstance().get(Calendar.MONTH) - calendar.get(Calendar.MONTH)) >= 0 ? age : age - 1;
        mEditTextAge.setText(String.valueOf(age));
    }

    private void loadData() {
        Database mDbHelper = new Database(mRootView.getContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        mPatient = Paciente.ConsultarChave(db, codPatient);
        if (mPatient != null) {
            mEditTextName.setText(mPatient.getNome());
            mEditTextBirthDate.setText(Util.ConverterData(mPatient.getDataNacimento()));
            updateAge(mPatient.getDataNacimento());
            mEditTextComments.setText(mPatient.getObservacao());
            mFabSelectedColor.setBackground(getColor(mPatient.getCor()));
            mCurrentColorSelectd = mPatient.getCor();
        } else {
            mCurrentColorSelectd = "26a69a";
        }

    }

    public void delete() {
        try {
            Database mDbHelper = new Database(mRootView.getContext());
            final SQLiteDatabase db = mDbHelper.getWritableDatabase();
            final Paciente patient = Paciente.ConsultarChave(db, codPatient);
            DialogApp.showDialogYesNo(getActivity(), getString(R.string.delete_text), getString(R.string.delete_patient_question_text), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Paciente.ExcluirSQL(db, patient);
                            Alarm.ExcluirSQL(db, patient);
                            getActivity().finish();
                        }
                    },
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

        } catch (Exception e) {
            Toast.makeText(this.getActivity(), e.getMessage(), Toast.LENGTH_SHORT);
        }
    }

    public void save(boolean closeActivity) {
        try {
            Database mDbHelper = new Database(mRootView.getContext());
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            Paciente patient;

            if (codPatient >= 0) {
                patient = Paciente.ConsultarChave(db, codPatient);
                patient.setNome(mEditTextName.getText().toString());
                patient.setDataNacimento(Util.ConverterStringDate(mEditTextBirthDate.getText().toString() + " 00:00:00"));
                patient.setObservacao(mEditTextComments.getText().toString());
                patient.setCor(mCurrentColorSelectd);
                Paciente.AlterarSQL(db, patient);
            } else {
                patient = new Paciente();
                patient.setCodigo(Paciente.ObterProximoCodigo(db));
                patient.setNome(mEditTextName.getText().toString());
                patient.setDataNacimento(Util.ConverterStringDate(mEditTextBirthDate.getText().toString() + " 00:00:00"));
                patient.setObservacao(mEditTextComments.getText().toString());
                patient.setCor(mCurrentColorSelectd);
                Paciente.InserirSQL(db, patient);
                codPatient = patient.getCodigo();
            }
            if (closeActivity)
                getActivity().finish();
        } catch (Exception e) {
            Toast.makeText(this.getActivity(), e.getMessage(), Toast.LENGTH_SHORT);
        }

    }
}
