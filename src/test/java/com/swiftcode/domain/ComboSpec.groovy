package com.swiftcode.domain

import com.swiftcode.service.PurchaseComboService
import spock.lang.Specification

class ComboSpec extends Specification {
    def purchaseService = Mock(PurchaseComboService)

    def "add new combo"() {
        when: "create a combo"
        def combo = new Combo("fakeComboName", 25, "500M", "100M", 2, 150)

        then: "have a combo"
        combo.quotaLimit == 500 * 1024 * 1024
    }

    /**
     * 根据不同的套餐返回不同的收款码
     */
    def "return different receipt code according to the different combo"() {
        given: "a user"
        def user = Mock(User)
        and: "25 yuan combo"
        def combo = new Combo("fakeComboName", 25, "500M", "100M", 2, 150)
        when: "user choose it"
        purchaseService.buy(user, combo) >> "/path/of/ReceiptCode/25.png"
        def receiptCode = purchaseService.buy(user, combo)
        then: "return a receipt code picture"
        receiptCode != null
        and: "that picture is 25.png"
        def receiptCodePicture = receiptCode as String
        int lastDashIndex = receiptCodePicture.lastIndexOf("/")
        def result = receiptCodePicture.substring(lastDashIndex + 1, receiptCodePicture.length())
        result == "25.png"
    }
}
