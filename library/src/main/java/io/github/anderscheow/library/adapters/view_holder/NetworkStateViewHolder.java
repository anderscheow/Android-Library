package io.github.anderscheow.library.adapters.view_holder;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import io.github.anderscheow.library.R2;
import io.github.anderscheow.library.constant.NetworkState;
import io.github.anderscheow.library.databinding.ViewNetworkStateBinding;

public class NetworkStateViewHolder extends MyBaseViewHolder<NetworkState> {

    @BindView(R2.id.button_retry)
    Button buttonRetry;

    @BindView(R2.id.text_view_error_msg)
    TextView textViewErrorMsg;
    private RetryCallback callback;
    @StringRes
    private Integer errorMessage;

    private NetworkStateViewHolder(ViewNetworkStateBinding binding, RetryCallback callback) {
        super(binding);

        this.callback = callback;
    }

    private NetworkStateViewHolder(ViewNetworkStateBinding binding, RetryCallback callback,
                                   @StringRes Integer errorMessage) {
        super(binding);

        this.callback = callback;
        this.errorMessage = errorMessage;
    }

    public static NetworkStateViewHolder create(@NonNull ViewNetworkStateBinding binding,
                                                RetryCallback callback) {
        return new NetworkStateViewHolder(binding, callback);
    }

    public static NetworkStateViewHolder create(@NonNull ViewNetworkStateBinding binding,
                                                RetryCallback callback,
                                                @StringRes Integer errorMessage) {
        return new NetworkStateViewHolder(binding, callback);
    }

    @Override
    protected void extraBinding(NetworkState networkState) {
        buttonRetry.setOnClickListener(v -> {
            if (callback != null) {
                callback.retry();
            }
        });

        if (errorMessage != null) {
            textViewErrorMsg.setText(errorMessage);
        }
    }

    @Override
    protected void onClick(@NonNull View view, NetworkState item) {

    }

    public interface RetryCallback {
        void retry();
    }
}
