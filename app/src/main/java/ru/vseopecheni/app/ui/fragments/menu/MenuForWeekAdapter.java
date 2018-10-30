package ru.vseopecheni.app.ui.fragments.menu;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.vseopecheni.app.R;
import ru.vseopecheni.app.data.models.ResponseType;
import ru.vseopecheni.app.ui.base.BaseViewHolder;
import ru.vseopecheni.app.utils.Constant;
import ru.vseopecheni.app.utils.ImageSaver;

public class MenuForWeekAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private ResponseType responseType;
    private Callback callback;
    private String type;

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MenuForWeekAdapter.MenuForWeekViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_for_week, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    public void setCallback(MenuWeekFragment menuWeekFragment) {
        this.callback = menuWeekFragment;
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public void setItems(ResponseType responseType, String type) {
        this.responseType = responseType;
        this.type = type;
    }

    public interface Callback {

        void showRecipe(String json);
    }

    public class MenuForWeekViewHolder extends BaseViewHolder {

        @BindView(R.id.type_menu_rv)
        TextView typeMenu;
        @BindView(R.id.image_menu)
        ImageView imageView;
        @BindView(R.id.title_menu_rv)
        TextView title;
        private Context context;

        MenuForWeekViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            try {
                if (position == 0) {
                    typeMenu.setText("Завтрак");
                    if (Constant.isInternet(context)) {
                        try {
                            if (responseType.getBreakfast().getImgUrl().equals("")) {
                                if (responseType.getBreakfast().getContent().getImage().contains("/f/")) {
                                    Glide.with(context)
                                            .asBitmap()
                                            .load("https://app.vseopecheni.ru" + responseType.getBreakfast().getContent().getImage())
                                            .apply(new RequestOptions().fitCenter())
                                            .into(imageView);
                                } else {
                                    Glide.with(context)
                                            .asBitmap()
                                            .load("https://app.vseopecheni.ru/" + responseType.getBreakfast().getContent().getImage())
                                            .apply(new RequestOptions().fitCenter())
                                            .into(imageView);
                                }
                            } else {
                                if (responseType.getBreakfast().getContent().getImage().contains("/f/")) {
                                    Glide.with(context)
                                            .asBitmap()
                                            .load("https://app.vseopecheni.ru" + responseType.getBreakfast().getImgUrl())
                                            .apply(new RequestOptions().fitCenter())
                                            .into(imageView);
                                } else {
                                    Glide.with(context)
                                            .asBitmap()
                                            .load("https://app.vseopecheni.ru/" + responseType.getBreakfast().getImgUrl())
                                            .apply(new RequestOptions().fitCenter())
                                            .into(imageView);
                                }
                            }
                        } catch (NullPointerException ignored) {
                        }
                    } else {
                        Bitmap bitmap = new ImageSaver(context).
                                setFileName("breakfast.jpg")
                                .setDirectoryName(type)
                                .load();
                        Glide.with(context)
                                .asBitmap()
                                .load(bitmap)
                                .apply(new RequestOptions().fitCenter())
                                .into(imageView);
                    }
                    if (responseType.getBreakfast().getContent().getTitle().equals("")) {
                        title.setText("Завтрак" + "\n" + responseType.getBreakfast().getTitle());
                    } else {
                        title.setText("Завтрак" + "\n" + responseType.getBreakfast().getContent().getTitle());
                    }
                    if (!responseType.getBreakfast().getContent().getId().equals("")) {
                        imageView.setOnClickListener(v -> {
                            callback.showRecipe(new Gson().toJson(responseType.getBreakfast().getContent()));
                        });
                    }
                }
                if (position == 1) {
                    typeMenu.setText("Второй завтрак");
                    if (Constant.isInternet(context)) {
                        if (responseType.getTiffin().getImgUrl().equals("")) {
                            if (responseType.getTiffin().getContent().getImage().contains("/f/")) {
                                Glide.with(context)
                                        .asBitmap()
                                        .load("https://app.vseopecheni.ru" + responseType.getTiffin().getContent().getImage())
                                        .apply(new RequestOptions().fitCenter())
                                        .into(imageView);
                            } else {
                                Glide.with(context)
                                        .asBitmap()
                                        .load("https://app.vseopecheni.ru/" + responseType.getTiffin().getContent().getImage())
                                        .apply(new RequestOptions().fitCenter())
                                        .into(imageView);
                            }
                        } else {
                            if (responseType.getTiffin().getContent().getImage().contains("/f/")) {
                                Glide.with(context)
                                        .asBitmap()
                                        .load("https://app.vseopecheni.ru" + responseType.getTiffin().getImgUrl())
                                        .apply(new RequestOptions().fitCenter())
                                        .into(imageView);
                            } else {
                                Glide.with(context)
                                        .asBitmap()
                                        .load("https://app.vseopecheni.ru/" + responseType.getTiffin().getImgUrl())
                                        .apply(new RequestOptions().fitCenter())
                                        .into(imageView);
                            }
                        }
                    } else {
                        Bitmap bitmap = new ImageSaver(context).
                                setFileName("tiffin.jpg")
                                .setDirectoryName(type)
                                .load();
                        Glide.with(context)
                                .asBitmap()
                                .load(bitmap)
                                .apply(new RequestOptions().fitCenter())
                                .into(imageView);
                    }

                    if (responseType.getTiffin().getContent().getTitle().equals("")) {
                        title.setText("Второй завтрак" + "\n" + responseType.getTiffin().getTitle());
                    } else {
                        title.setText("Второй завтрак" + "\n" + responseType.getTiffin().getContent().getTitle());
                    }
                    if (!responseType.getTiffin().getContent().getId().equals("")) {
                        imageView.setOnClickListener(v -> {
                            callback.showRecipe(new Gson().toJson(responseType.getTiffin().getContent()));
                        });
                    }
                }
                if (position == 2) {
                    typeMenu.setText("Обед");
                    if (Constant.isInternet(context)) {
                        if (responseType.getLunch().getImgUrl().equals("")) {
                            if (responseType.getLunch().getContent().getImage().contains("/f/")) {
                                Glide.with(context)
                                        .asBitmap()
                                        .load("https://app.vseopecheni.ru" + responseType.getLunch().getContent().getImage())
                                        .apply(new RequestOptions().fitCenter())
                                        .into(imageView);
                            } else {
                                Glide.with(context)
                                        .asBitmap()
                                        .load("https://app.vseopecheni.ru/" + responseType.getLunch().getContent().getImage())
                                        .apply(new RequestOptions().fitCenter())
                                        .into(imageView);
                            }
                        } else {
                            if (responseType.getLunch().getContent().getImage().contains("/f/")) {
                                Glide.with(context)
                                        .asBitmap()
                                        .load("https://app.vseopecheni.ru" + responseType.getLunch().getImgUrl())
                                        .apply(new RequestOptions().fitCenter())
                                        .into(imageView);
                            } else {
                                Glide.with(context)
                                        .asBitmap()
                                        .load("https://app.vseopecheni.ru/" + responseType.getLunch().getImgUrl())
                                        .apply(new RequestOptions().fitCenter())
                                        .into(imageView);
                            }
                        }
                    } else {
                        Bitmap bitmap = new ImageSaver(context).
                                setFileName("lunch.jpg")
                                .setDirectoryName(type)
                                .load();
                        Glide.with(context)
                                .asBitmap()
                                .load(bitmap)
                                .apply(new RequestOptions().fitCenter())
                                .into(imageView);
                    }
                    if (responseType.getLunch().getContent().getTitle().equals("")) {
                        title.setText("Обед" + "\n" + responseType.getLunch().getTitle());
                    } else {
                        title.setText("Обед" + "\n" + responseType.getLunch().getContent().getTitle());
                    }
                    if (!responseType.getLunch().getContent().getId().equals("")) {
                        imageView.setOnClickListener(v -> {
                            callback.showRecipe(new Gson().toJson(responseType.getLunch().getContent()));
                        });
                    }
                }
                if (position == 3) {
                    typeMenu.setText("Полдник");
                    if (Constant.isInternet(context)) {
                        if (responseType.getAfternoon().getImgUrl().equals("")) {
                            if (responseType.getAfternoon().getContent().getImage().contains("/f/")) {
                                Glide.with(context)
                                        .asBitmap()
                                        .load("https://app.vseopecheni.ru" + responseType.getAfternoon().getContent().getImage())
                                        .apply(new RequestOptions().fitCenter())
                                        .into(imageView);
                            } else {
                                Glide.with(context)
                                        .asBitmap()
                                        .load("https://app.vseopecheni.ru/" + responseType.getAfternoon().getContent().getImage())
                                        .apply(new RequestOptions().fitCenter())
                                        .into(imageView);
                            }
                        } else {
                            if (responseType.getAfternoon().getContent().getImage().contains("/f/")) {
                                Glide.with(context)
                                        .asBitmap()
                                        .load("https://app.vseopecheni.ru" + responseType.getAfternoon().getImgUrl())
                                        .apply(new RequestOptions().fitCenter())
                                        .into(imageView);
                            } else {
                                Glide.with(context)
                                        .asBitmap()
                                        .load("https://app.vseopecheni.ru/" + responseType.getAfternoon().getImgUrl())
                                        .apply(new RequestOptions().fitCenter())
                                        .into(imageView);
                            }
                        }
                    } else {
                        Bitmap bitmap = new ImageSaver(context).
                                setFileName("afternoon.jpg")
                                .setDirectoryName(type)
                                .load();
                        Glide.with(context)
                                .asBitmap()
                                .load(bitmap)
                                .apply(new RequestOptions().fitCenter())
                                .into(imageView);
                    }
                    if (responseType.getAfternoon().getContent().getTitle().equals("")) {
                        title.setText("Полдник" + "\n" + responseType.getAfternoon().getTitle());
                    } else {
                        title.setText("Полдник" + "\n" + responseType.getAfternoon().getContent().getTitle());
                    }
                    if (!responseType.getAfternoon().getContent().getId().equals("")) {
                        imageView.setOnClickListener(v -> {
                            callback.showRecipe(new Gson().toJson(responseType.getAfternoon().getContent()));
                        });
                    }
                }
                if (position == 4) {
                    typeMenu.setText("Ужин");
                    if (Constant.isInternet(context)) {
                        if (responseType.getDinner().getImgUrl().equals("")) {
                            if (responseType.getDinner().getContent().getImage().contains("/f/")) {
                                Glide.with(context)
                                        .asBitmap()
                                        .load("https://app.vseopecheni.ru" + responseType.getDinner().getContent().getImage())
                                        .apply(new RequestOptions().fitCenter())
                                        .into(imageView);
                            } else {
                                Glide.with(context)
                                        .asBitmap()
                                        .load("https://app.vseopecheni.ru/" + responseType.getDinner().getContent().getImage())
                                        .apply(new RequestOptions().fitCenter())
                                        .into(imageView);
                            }
                        } else {
                            if (responseType.getDinner().getContent().getImage().contains("/f/")) {
                                Glide.with(context)
                                        .asBitmap()
                                        .load("https://app.vseopecheni.ru" + responseType.getDinner().getImgUrl())
                                        .apply(new RequestOptions().fitCenter())
                                        .into(imageView);
                            } else {
                                Glide.with(context)
                                        .asBitmap()
                                        .load("https://app.vseopecheni.ru/" + responseType.getDinner().getImgUrl())
                                        .apply(new RequestOptions().fitCenter())
                                        .into(imageView);
                            }
                        }
                    } else {
                        Bitmap bitmap = new ImageSaver(context).
                                setFileName("dinner.jpg")
                                .setDirectoryName(type)
                                .load();
                        Glide.with(context)
                                .asBitmap()
                                .load(bitmap)
                                .apply(new RequestOptions().fitCenter())
                                .into(imageView);
                    }
                    if (responseType.getDinner().getContent().getTitle().equals("")) {
                        title.setText("Ужин" + "\n" + responseType.getDinner().getTitle());
                    } else {
                        title.setText("Ужин" + "\n" + responseType.getDinner().getContent().getTitle());
                    }
                    if (!responseType.getDinner().getContent().getId().equals("")) {
                        imageView.setOnClickListener(v -> {
                            callback.showRecipe(new Gson().toJson(responseType.getDinner().getContent()));
                        });
                    }
                }
            } catch (NullPointerException e) {
                Snackbar.make(imageView, "Ошибка получения данных, подключите интернет", Snackbar.LENGTH_LONG).show();
            }

        }

    }
}
