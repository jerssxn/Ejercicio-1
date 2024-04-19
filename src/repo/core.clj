(def factura
  {:invoice/id    "i1"
   :invoice/items [{:invoice-item/id          "ii1"
                    :invoice-item/sku         "SKU 1"
                    :taxable/taxes            [{:tax/id       "t1"
                                                :tax/category :iva
                                                :tax/rate     19}]
                    :retentionable/retentions [{:retention/id       "r1"
                                                :retention/category :ret_fuente
                                                :retention/rate     1}]}
                   {:invoice-item/id  "ii2"
                    :invoice-item/sku "SKU 2"
                    :taxable/taxes    [{:tax/id       "t2"
                                        :tax/category :iva
                                        :tax/rate     16}]}
                   {:invoice-item/id  "ii3"
                    :invoice-item/sku "SKU 3"
                    :taxable/taxes    [{:tax/id       "t3"
                                        :tax/category :iva
                                        :tax/rate     19}]}
                   {:invoice-item/id          "ii4"
                    :invoice-item/sku         "SKU 3"
                    :retentionable/retentions [{:retention/id       "r2"
                                                :retention/category :ret_fuente
                                                :retention/rate     1}]}
                   {:invoice-item/id          "ii5"
                    :invoice-item/sku         "SKU 4"
                    :retentionable/retentions [{:retention/id       "r3"
                                                :retention/category :ret_fuente
                                                :retention/rate     2}]}]})

(defn filtrar-articulos [factura]
  (let [iva-19? #(= (:tax/rate (first (:taxable/taxes %))) 19)
        retencion-1? #(= (:retention/rate (first (:retentionable/retentions %))) 1)]
    (->> factura
         :invoice/items
         (filter #(or (and (iva-19? %) (not (retencion-1? %)))
                      (and (not (iva-19? %)) (retencion-1? %)))))))

(def articulos_filtrados
  (filtrar-articulos factura))