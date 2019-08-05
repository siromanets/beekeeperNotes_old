package com.example.key.beekeepernote.login;

import android.app.AlertDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.bumptech.glide.load.engine.Resource;
import com.example.key.beekeepernote.R;
import com.example.key.beekeepernote.databinding.ConfirmFragmentBinding;
import com.example.key.beekeepernote.utils.ConnectionUtils;

import java.util.Objects;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import dmax.dialog.SpotsDialog;

public class ConfirmSMSFragment extends Fragment implements TextWatcher {

    private static final String TAG = "ConfirmSMSFragment";

    private static final String SIGN_IN_DATA = "sign_in_data";

    private static final int CONFIRMATION_SMS_CODE_LENGTH = 4;

    private ConfirmFragmentBinding binding;

    private NavController navController;

    private ConfirmSMSFragmentVM viewModel;

    private AlertDialog progressDialog;

//    public static Bundle createBundle(ResponseSignInData data) {
//        Bundle args = new Bundle();
//        args.putParcelable(SIGN_IN_DATA, data);
//        return args;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_confirm_sm, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        navController = Navigation.findNavController(binding.getRoot());
        binding.confirmButton.setOnClickListener((v) -> onAcceptButtonClick());
        binding.resendSmsButton.setOnClickListener((v) -> onResendButtonClick());
        binding.codeInput.addTextChangedListener(this);
        initUserData();
    }

    private void onResendSMS(Resource<Integer> resource) {
//        if (resource.status == Status.SUCCESS){
//        //    Snackbar.make(binding.getRoot(), R.string.sms_was_send, Snackbar.LENGTH_LONG).show();
//        }else {
//         //   Snackbar.make(binding.getRoot(), resource.message, Snackbar.LENGTH_SHORT).show();
//        }
    }

//    private void verificationResponse(Resource<ResponseVerificationData> responseVerificationDataResource) {
//        switch (responseVerificationDataResource.status) {
//            case Status.SUCCESS:
//                hideProgress();
//                showSetPasswordFragment(responseVerificationDataResource.data);
//                break;
//            case Status.ERROR:
//                Snackbar.make(binding.getRoot(), responseVerificationDataResource.message, Snackbar.LENGTH_LONG).show();
//                hideProgress();
//                break;
//            case Status.LOADING:
//                showProgress();
//                break;
//            default:
//                break;
//        }
//    }

//    private void showSetPasswordFragment(ResponseVerificationData data) {
//        navController.navigate(R.id.action_confirmSMSFragment_to_setPasswordFragment,
//                SetPasswordFragment.createBundle(data));
//    }

    private void showProgress() {
        progressDialog = new SpotsDialog.Builder()
                .setContext(getContext())
                .build();
        progressDialog.show();
    }

    private void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    private void initUserData() {
//        final ConfirmSMSFragmentArgs arguments = ConfirmSMSFragmentArgs.fromBundle(getArguments());
//        if (arguments.getSignInData() != null) {
//            userVerification = arguments.getSignInData();
//        } else {
//            throw new RuntimeException("Verification details are not provided");
//        }
    }

    private void onAcceptButtonClick() {
        hideSystemKeyBoard();
//        if (ConnectionUtils.isConnected(Objects.requireNonNull(getContext()))) {
//            final String code = binding.codeInput.getText().toString();
//            viewModel.onVerificationUser(userVerification, Integer.parseInt(code));
//        } else {
//            showNoConnectionToast();
//        }
    }

    private void hideSystemKeyBoard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void showNoConnectionToast() {
       // Snackbar.make(binding.getRoot(), R.string.no_internet_connection, Snackbar.LENGTH_LONG).show();
    }

    private void enableVerifyButton(boolean enabled) {
        binding.confirmButton.setEnabled(enabled);
        binding.confirmButton.setClickable(enabled);
    }

    private void onResendButtonClick() {
        hideSystemKeyBoard();
        if (ConnectionUtils.isConnected(Objects.requireNonNull(getContext()))) {
        //    viewModel.onResendSMS(userVerification);
        } else {
            showNoConnectionToast();
        }
    }

    public boolean isValidCodeInput() {
        return !TextUtils.isEmpty(binding.codeInput.getText().toString())
                || binding.codeInput.getText().toString().length() == CONFIRMATION_SMS_CODE_LENGTH;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        enableVerifyButton(isValidCodeInput());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
