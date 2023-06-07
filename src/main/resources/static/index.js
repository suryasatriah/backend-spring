const accountAPI = "http://localhost:8080/api/account/";
const transactionAPI = "http://localhost:8080/api/transaction/";

function AccountTable() {
  $("#AccountTable").empty();
  $.getJSON(accountAPI, function (json) {
    var tr = [];
    tr.push("<thead>");
    tr.push("<tr>");
    tr.push("<th>ID</th>");
    tr.push("<th>Nama</th>");
    tr.push("</tr>");
    tr.push("</thead>");
    tr.push("<tbody >");

    for (var i = 0; i < json.length; i++) {
      tr.push("<tr>");
      tr.push("<td>" + json[i].id + "</td>");
      tr.push("<td>" + json[i].name + "</td>");
    }
    tr.push("</tbody>");
    $("#AccountTable").append($(tr.join("")));
  });
}

function addAccount() {
  $("#addAccountForm").validate({
    submitHandler: function () {
      var inputName = $("#name").val();
      $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: accountAPI + "add",
        data: JSON.stringify({
          name: inputName,
          point: 0,
        }),
        cache: false,
        success: function (response) {
          //Handler
        },
        error: function (error) {
          // Handler
        },
      });
    },
  });
}

function TransactionTable() {
  $("#TransactionTable").empty();
  $.getJSON(transactionAPI, function (json) {
    var tr = [];
    tr.push("<thead>");
    tr.push("<tr>");
    tr.push("<th>AccountID</th>");
    tr.push("<th>Transaction Date</th>");
    tr.push("<th>Description</th>");
    tr.push("<th>Debit/Credit</th>");
    tr.push("<th>Amount</th>");
    tr.push("</tr>");
    tr.push("</thead>");
    tr.push("<tbody >");

    for (var i = 0; i < json.length; i++) {
      tr.push("<tr>");
      tr.push("<td>" + json[i].account.id + "</td>");
      tr.push("<td>" + json[i].date + "</td>");
      tr.push("<td>" + json[i].description + "</td>");
      tr.push("<td>" + json[i].debitCreditStatus + "</td>");
      tr.push("<td>" + json[i].amount + "</td>");
      tr.push("</tr>");
    }
    tr.push("</tbody>");
    $("#TransactionTable").append($(tr.join("")));
  });
}

function addTransaction() {
  $("#addTransactionForm").validate({
    submitHandler: function () {
      var inputId = $("#account_id").val();
      var inputDate = $("#date").val();
      var inputDescription = $("#description").val();
      var inputDebitCreditStatus = $("#debitCreditStatus").val();
      var inputAmount = $("#amount").val();
      var inputData = {
        account: {
          id: inputId,
        },
        date: inputDate,
        description: inputDescription,
        debitCreditStatus: inputDebitCreditStatus,
        amount: inputAmount,
      };
      $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: transactionAPI + "add",
        data: JSON.stringify(inputData),
        cache: false,
        success: function (response) {
          // handler
        },
        error: function (error) {
          // Handler
        },
        complete: function () {
          location.reload(true);
        },
      });
    },
  });
}

function PointTable() {
  $("#PointTable").empty();
  $.getJSON(accountAPI, function (json) {
    var tr = [];
    tr.push("<thead>");
    tr.push("<tr>");
    tr.push("<th>AccountID</th>");
    tr.push("<th>Name</th>");
    tr.push("<th>Total Point</th>");
    tr.push("</tr>");
    tr.push("</thead>");
    tr.push("<tbody >");

    for (var i = 0; i < json.length; i++) {
      tr.push("<tr>");
      tr.push("<td>" + json[i].id + "</td>");
      tr.push("<td>" + json[i].name + "</td>");
      tr.push("<td>" + json[i].point + "</td>");
      tr.push("</tr>");
    }
    tr.push("</tbody>");
    $("#PointTable").append($(tr.join("")));
  });
}

function SavingTable(startDate, endDate) {
  $("#SavingTable").empty();
  $.getJSON(transactionAPI + startDate + "/" + endDate, function (json) {
    var tr = [];
    tr.push("<thead>");
    tr.push("<tr>");
    tr.push("<th>Transaction Date</th>");
    tr.push("<th>Description</th>");
    tr.push("<th>Credit</th>");
    tr.push("<th>Debit</th>");
    tr.push("<th>Amount</th>");
    tr.push("</tr>");
    tr.push("</thead>");
    tr.push("<tbody >");

    for (var i = 0; i < json.length; i++) {
      tr.push("<tr>");
      tr.push("<td>" + json[i].date + "</td>");
      tr.push("<td>" + json[i].description + "</td>");
      if (json[i].debitCreditStatus.toUpperCase() === "C") {
        tr.push("<td>" + json[i].amount + "</td>");
        tr.push("<td>" + "-" + "</td>");
      } else {
        tr.push("<td>" + "-" + "</td>");
        tr.push("<td>" + json[i].amount + "</td>");
      }
      tr.push("<td>" + json[i].amount + "</td>");
      tr.push("</tr>");
    }
    tr.push("</tbody>");
    $("#SavingTable").append($(tr.join("")));
  });
}

function setDateInterval() {
  var startDate;
  var endDate;
  $("#setDateIntervalForm").validate({
    submitHandler: function () {
      startDate = $("#start-date").val();
      endDate = $("#end-date").val();
      SavingTable(startDate, endDate);
    },
  });
}

function clickHandler() {
  $("#reset-account").click(function () {
    let confirm = window.confirm(
      "Are you sure you want to delete all account?(Warning: must delete all transaction first due to foreign key rule ya:))"
    );
    if (confirm) {
      $.ajax({
        type: "DELETE",
        url: accountAPI + "reset",
        cache: false,
        success: function (response) {
          //Handler
        },
        error: function (error) {
          // Handler
        },
      });
    }
  });
  $("#reset-transaction").click(function () {
    let confirm = window.confirm(
      "Are you sure you want to delete all transaction?"
    );
    if (confirm) {
      $.ajax({
        type: "DELETE",
        url: transactionAPI + "reset",
        cache: false,
        success: function (response) {
          //Handler
        },
        error: function (error) {
          // Handler
        },
      });
    }
  });
}

$(document).ready(function () {
  AccountTable();
  TransactionTable();
  PointTable();
  SavingTable();
  addAccount();
  addTransaction();
  setDateInterval();
  clickHandler();
});
