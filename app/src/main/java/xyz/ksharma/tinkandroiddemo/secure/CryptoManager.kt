package xyz.ksharma.tinkandroiddemo.secure

import androidx.security.crypto.MasterKeys
import com.google.crypto.tink.integration.android.AndroidKeystoreAesGcm

object CryptoManager {

    // Get / Create Keys using Android Keystore
    private val aesKeystore =
        AndroidKeystoreAesGcm(MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC))

    fun encrypt(plainText: String): ByteArray =
        aesKeystore.encrypt(plainText.toByteArray(), "Data".toByteArray())

    fun decrypt(cipherText: ByteArray): String {
        val plaintTextBytes =
            aesKeystore.decrypt(cipherText, "Data".toByteArray())
        return String(plaintTextBytes)
    }
}
