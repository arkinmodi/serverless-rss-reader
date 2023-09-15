resource "aws_sqs_queue" "serverless_rss_reader" {
  name                       = var.serverless_rss_reader_sqs_name
  visibility_timeout_seconds = 60
}

resource "aws_lambda_event_source_mapping" "rss_handler" {
  event_source_arn = aws_sqs_queue.serverless_rss_reader.arn
  function_name    = aws_lambda_function.rss_handler.arn
}

data "aws_iam_policy_document" "discord_handler_sqs" {
  statement {
    actions   = ["sqs:SendMessage"]
    resources = [aws_sqs_queue.serverless_rss_reader.arn]
  }
}

resource "aws_iam_role_policy" "discord_handler_sqs" {
  role   = aws_iam_role.discord_handler.name
  policy = data.aws_iam_policy_document.discord_handler_sqs.json
}

data "aws_iam_policy_document" "rss_handler_sqs" {
  statement {
    actions = [
      "sqs:DeleteMessage",
      "sqs:GetQueueAttributes",
      "sqs:ReceiveMessage",
    ]
    resources = [aws_sqs_queue.serverless_rss_reader.arn]
  }
}

resource "aws_iam_role_policy" "rss_handler_sqs" {
  role   = aws_iam_role.rss_handler.name
  policy = data.aws_iam_policy_document.rss_handler_sqs.json
}
