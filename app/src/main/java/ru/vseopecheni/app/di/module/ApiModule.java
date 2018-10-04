package ru.vseopecheni.app.di.module;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import ru.vseopecheni.app.BuildConfig;
import ru.vseopecheni.app.data.network.ApiMethods;
import ru.vseopecheni.app.data.network.ServiceNetwork;
import ru.vseopecheni.app.data.network.ServiceNetworkImp;
import ru.vseopecheni.app.utils.Constant;

/**
 * модуль предоставляет зависимости для АПИ-шки
 * данные сущности добавляются в "зависимые" объекты через конструктор.
 * через ту же аннотацию {@link javax.inject.Inject}.
 * Dagger 2 умеет предоставлять зависимости в поля, конструкторы и через методы, при добавленной
 * аннотации.
 */
@Module
public class ApiModule {

    @Provides
    @Singleton
    ServiceNetwork provideServiceNetwork(ServiceNetworkImp serviceNetwork) {
        return serviceNetwork;
    }

    @Provides
    @Singleton
    ApiMethods provideApiMethodsService(Retrofit retrofit) {
        return retrofit.create(ApiMethods.class);
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(HttpUrl baseUrl, Converter.Factory converterFactory, CallAdapter.Factory callAdapterFactory, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addCallAdapterFactory(callAdapterFactory)
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    public Converter.Factory provideGsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Singleton
    @Provides
    public Gson provideGson() {

        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    @Provides
    @Singleton
    public CallAdapter.Factory provideRxJavaCallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    @Singleton
    HttpUrl provideHttpUrl() {
        return HttpUrl.parse(Constant.BASE_URI);
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(5, TimeUnit.SECONDS).writeTimeout(5, TimeUnit.SECONDS).connectTimeout(5, TimeUnit.SECONDS);
        //                модификация запроса
        //                .addInterceptor()
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        return builder.build();
    }
}
