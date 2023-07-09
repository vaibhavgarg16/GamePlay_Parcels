package com.game.playparcels.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import android.text.Spanned;
import android.widget.TextView;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.LinearLayout;

import com.game.playparcels.R;


public class TermsNConditions extends AppCompatActivity {

    LinearLayout termsbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_n_conditions);

        termsbtn = findViewById(R.id.News);

        termsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });





        String htmlString = "<h1><u>Please read through the terms and conditions.</u></h1> <br> <p>Before we can accept your application and make a legally enforceable agreement without further reference to you, you must read these terms and conditions to make sure that they contain all that you want and nothing that you are not happy with.</p> <br> <h3><u>Application</u></h3><br><p>The Terms and Conditions will apply to the purchase of the services and goods by you (the Customer or you). We are GamePlayParcels with email address: support@gameplayparcels.co.uk; (the Supplier or us or we).\n" +
                "These are the terms on which we sell all Services to you. By ordering any of the Services, you agree to be bound by these Terms and Conditions. You can only purchase the Services and Goods from the Website if you are eligible to enter into a contract and are at least 18 years old.</p><br><h3><u>Interpretation</u></h3><br><p><b>Consumer</b> means an individual acting for purposes which are wholly or mainly outside his or her trade, business, craft or profession;</p><br><p><b>Contract</b> means the legally-binding agreement between you and us for the supply of the Services;</p><br><p><b>Delivery" +
                "</b> Location means the Supplier’s premises or other location where the Services are to be supplied, as set out in the Order;</p><br><p><b>Durable Medium</b> means paper or email, or any other medium that allows information to be addressed personally to the recipient, enables the recipient to store the information in a way accessible for future reference for a period that is long enough for the purposes of the information, and allows the unchanged reproduction of the information stored;</p><br><p><b>Goods</b> mean any goods that we supply to you with the Services, of the number and description as set out in the Order;</p><br>" +
                "<p><b>Order<b> means the Customer’s order for the Services from the Supplier as submitted following the step by step process set out on the Website;</p><br><p><b>Privacy Policy</b> means the terms which set out how we will deal with confidential and personal information received from you via the Website;</p><br><p><b>Services<b> means the services advertised on the Website, including any Goods, of the number and description set out in the Order;</p><br><p><b>Website</b> means our website gameplayparcels.co.uk on which the Services and Products are advertised.</p>" +
                "<br><h3><u>Services</u></h3><br><p>The description of the Services and any Goods is as set out in the Website, catalogues, brochures or other form of advertisement. Any description is for illustrative purposes only and there may be small discrepancies in the size and colour of any Goods supplied.\n" +
                "In the case of Services and any Goods made to your special requirements, it is your responsibility to ensure that any information or specification you provide is accurate.\n" +
                "All Services which appear on the Website are subject to availability. Depending on the stock levels we may be unable to dispatch the specified edition of the product chosen in such cases we will instead dispatch the customer a standard edition of such product.\n" +
                "We can make changes to the Services which are necessary to comply with any applicable law or safety requirement. We will notify you of these changes.</p><br><h3><u>Customer responsibilities</u><h3><br><p>You must co-operate with us in all matters relating to the Services, provide us and our authorised employees and representatives with access to any premises under your control as required, provide us with all information required to perform the Services and obtain any necessary licences and consents (unless otherwise agreed).\n" +
                "Failure to comply with the above is a Customer default which entitles us to suspend performance of the Services until you remedy it or if you fail to remedy it following our request, we can terminate the Contract with immediate effect on written notice to you.\n" +
                "Personal information and Registration.</p><br><p>When registering to use the Website you must set up a username and password. You remain responsible for all actions taken under the chosen username and password and undertake not to disclose your username and password to anyone else and keep them secret.\n" +
                "We retain and use all information strictly under the Privacy Policy.\n" +
                "We may contact you by using e-mail or other electronic communication methods and by pre-paid post and you expressly agree to this.</p><br><p>Upon receiving your product you accept full liability and must ensure that the product does not come into damage, cosmetic or functional.</p><br><h3><u>Basis of Services</u></h3><br><p>The description of the Services in our website do not constitute a contractual offer to sell the Services or Goods. When an Order has been submitted on the Website, we can reject it for any reason, although we will try to tell you the reason without delay.\n" +
                "The Order process is set out on the Website. Each step allows you to check and amend any errors before submitting the Order. It is your responsibility to check that you have used the ordering process correctly.</p><br><p>" +
                "A Contract will be formed for the Services ordered only when you receive an email from us confirming the Order (Order Confirmation). You must ensure that the Order Confirmation is complete and accurate and inform us immediately of any errors. We are not responsible for any inaccuracies in the Order placed by you. By placing an Order you agree to us giving you confirmation of the Contract by means of an email with all information in it (ie the Order Confirmation). You will receive the Order Confirmation within a reasonable time after making the Contract, but in any event not later than the delivery of any Goods supplied under the Contract, and before performance begins of any of the Services.</p>" +
                "<br><p>Any quotation or estimate of Fees (as defined below) is valid for a maximum period of 28 days  from its date, unless we expressly withdraw it at an earlier time.\n" +
                "No variation of the Contract, whether about description of the Services, Fees or otherwise, can be made after it has been entered into unless the variation is agreed by the Customer and the Supplier in writing.</p><br><p>We intend that these Terms and Conditions apply only to a Contract entered into by you as a Consumer (you are prohibited to act as a supplier to the products which you receive from us).</p>" +
                "<br><h3><u>Fees</u><h3><br><p>The fees (Fees) for the Services, the price of any Goods (if not included in the Fees) and any additional delivery or other charges is that set out on the Website at the date we accept the Order or such other price as we may agree in writing. Prices for Services may be calculated on a fixed price or on a standard monthly rate basis.\n" +
                "You must pay by submitting your credit or debit card details with your Order and we can take payment immediately or otherwise after the duration of the free trial (we may pre authorise the payment during the trial).</p><br>" +
                "<p>By subscribing to our services You agree to honor all of our policies. We reserve the right to also charge you a standard fee of £50 Only If we believe that a game was not returned due to fraudulent reasons, or if a disc was damaged accidentally or maliciously. (in all situations we highly recommend you obtain a receipt of proof from your local post office to eliminate accountability for loss of goods) .\n" +
                "Delivery</p><br><p>Risk of damage to, or loss of, any Goods will pass to you when the Goods are delivered to you.\n" +
                "You do not own the Goods. If full payment is overdue or a step occurs towards your bankruptcy, we can choose, by notice to cancel any delivery and end any right to use the Goods still leased to you, in which case you must return them.\n" +
                "</p><br><h3><u>Cancellations</u></h3><br><p>Subject as stated in these Terms and Conditions, you can cancel this contract within 14 days without giving any reason.\n" +
                "In this contract for the supply of goods over time (ie subscriptions), the right to cancel will be 14 days after the first delivery.\n" +
                "To exercise the right to cancel, you must inform us of your decision to cancel this Contract by a clear statement setting out your decision (eg a letter sent by post, fax or email).</p><br>" +
                "<p>If you have received Goods in connection with the Contract which you have cancelled, you must send back the Goods or hand them over to us at FREEPOST Gameplayparcels  without delay and in any event not later than 14 days from the day on which you communicate to us your cancellation of this Contract. The deadline is met if you send back the Goods before the period of 14 days has expired. You agree that you will have to bear the standard fee of £50 for each product that is not returned.</p>" +
                "<br><h3><u>Privacy Policy</u></h3><br><p>Your privacy is critical to us. We respect your privacy and comply with the General Data Protection Regulation with regard to your personal information.\n" +
                "These Terms and Conditions should be read alongside, and are in addition to our policies, including our privacy policy which can be visited at <b>(www.gameplayparcels.co.uk/privacy-policy)</b></p>";

        Spanned spanned = HtmlCompat.fromHtml(htmlString, HtmlCompat.FROM_HTML_MODE_COMPACT);

        TextView tvOutput = (TextView) findViewById(R.id.tv_log);

        tvOutput.setText(spanned);

        TextView tv_log = (TextView) findViewById(R.id.tv_log);
        tv_log.setMovementMethod(new ScrollingMovementMethod());
    }
}