package com.swiftcode.domain

import com.swiftcode.service.PurchasePlanService
import spock.lang.Specification

class PlanSpec extends Specification {
    def purchaseService = Mock(PurchasePlanService)

    def "add new plan"() {
        when: "create a plan"
        def combo = new Plan("fakePlanName", 25, "500M", "100M", 2, 150)

        then: "have a plan"
        combo.trafficLimit == 500 * 1024 * 1024
    }

    /**
     * 根据不同的套餐返回不同的收款码
     */
    def "return corresponding receipt code according to the specified plan"() {
        given: "a user"
        def user = Mock(User)
        and: "25 yuan plan"
        def combo = new Plan("fakePlanName", 25, "500M", "100M", 2, 150)
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
